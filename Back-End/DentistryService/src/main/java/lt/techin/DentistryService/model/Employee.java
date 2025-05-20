package lt.techin.DentistryService.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee {

  @Id
  private String licenseNumber;

  private String name;
  private String lastName;
  private String qualification;

  @ElementCollection
  @CollectionTable(name = "employee_treatments", joinColumns = @JoinColumn(name = "employee_license_number"))
  @Column(name = "treatment")
  private List<String> treatments;

  @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Appointment> appointments = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "provider_license_number")
  private UserProvider provider;

  public Employee(String name, String lastName, String qualification, List<String> treatments, List<Appointment> appointments, UserProvider provider) {
    this.name = name;
    this.lastName = lastName;
    this.qualification = qualification;
    this.treatments = treatments;
    this.appointments = appointments;
    this.provider = provider;
  }

  public Employee() {
  }

  public String getLicenseNumber() {
    return licenseNumber;
  }

  public void setLicenseNumber(String licenseNumber) {
    this.licenseNumber = licenseNumber;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getQualification() {
    return qualification;
  }

  public void setQualification(String qualification) {
    this.qualification = qualification;
  }

  public List<String> getTreatments() {
    return treatments;
  }

  public void setTreatments(List<String> treatments) {
    this.treatments = treatments;
  }

  public List<Appointment> getAppointments() {
    return appointments;
  }

  public void setAppointments(List<Appointment> appointments) {
    this.appointments = appointments;
  }

  public UserProvider getProvider() {
    return provider;
  }

  public void setProvider(UserProvider provider) {
    this.provider = provider;
  }
}
