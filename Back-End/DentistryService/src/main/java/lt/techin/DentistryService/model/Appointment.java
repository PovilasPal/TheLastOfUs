package lt.techin.DentistryService.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
public class Appointment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDate date;

  private LocalTime startTime;
  private LocalTime endTime;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "employee_license_number")
  private Employee employee;

  public Appointment(LocalDate date, LocalTime startTime, LocalTime endTime, Employee employee) {
    this.date = date;
    this.startTime = startTime;
    this.endTime = endTime;
    this.employee = employee;
  }

  public Appointment() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
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

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }
}
