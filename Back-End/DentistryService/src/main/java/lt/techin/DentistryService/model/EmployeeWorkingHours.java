package lt.techin.DentistryService.model;

import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.time.LocalTime;


@Entity
@Table(name = "employee_working_hours")
public class EmployeeWorkingHours {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "employee_id", nullable = false)
  private Employee employee;

  @Enumerated(EnumType.STRING)
  private DayOfWeek dayOfWeek;

  private LocalTime startTime;
  private LocalTime endTime;

  public EmployeeWorkingHours(Employee employee, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
    this.employee = employee;
    this.dayOfWeek = dayOfWeek;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public EmployeeWorkingHours() {
  }

  public Long getId() {
    return id;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public DayOfWeek getDayOfWeek() {
    return dayOfWeek;
  }

  public void setDayOfWeek(DayOfWeek dayOfWeek) {
    this.dayOfWeek = dayOfWeek;
  }

  public LocalTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalTime startTime) {
    this.startTime = startTime;
  }

  public LocalTime getEndTime() {
    return endTime;
  }

  public void setEndTime(LocalTime endTime) {
    this.endTime = endTime;
  }
}
