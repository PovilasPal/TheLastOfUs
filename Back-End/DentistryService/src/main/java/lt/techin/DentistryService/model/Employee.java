package lt.techin.DentistryService.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  @JoinColumn(name = "provider_licence_number", referencedColumnName = "licence_number", nullable = false)
  private UserProvider userProvider;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(nullable = false, unique = true)
  private String licenceNumber;
  @Column
  private String qualification;
  @Column
  private String service;

  @Column(name = "is_active", columnDefinition = "boolean default true")
  private boolean isActive = true;

  @Column(name = "created_at", updatable = false)
  @CreationTimestamp
  private LocalDateTime appointment;

  public Employee(UserProvider userProvider, String firstName, String lastName, String licenceNumber, String qualification, String service) {
    this.userProvider = userProvider;
    this.firstName = firstName;
    this.lastName = lastName;
    this.licenceNumber = licenceNumber;
    this.qualification = qualification;
    this.service = service;
    this.appointment = LocalDateTime.now();
    this.isActive = true;
  }

  public Employee() {
  }

  public Long getId() {
    return id;
  }

  public UserProvider getUserProvider() {
    return userProvider;
  }

  public void setUserProvider(UserProvider userProvider) {
    this.userProvider = userProvider;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getLicenceNumber() {
    return licenceNumber;
  }

  public void setLicenceNumber(String licenceNumber) {
    this.licenceNumber = licenceNumber;
  }

  public String getQualification() {
    return qualification;
  }

  public void setQualification(String qualification) {
    this.qualification = qualification;
  }

  public String getService() {
    return service;
  }

  public void setService(String services) {
    this.service = service;
  }

  public LocalDateTime getAppointment() {
    return appointment;
  }

  public void setAppointment(LocalDateTime appointment) {
    this.appointment = appointment;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }
}



