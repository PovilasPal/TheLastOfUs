package lt.techin.DentistryService.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "employees")
public class Employee {

  @Id
  private String licenseNumber;

  private String name;
  private String lastName;
  private String qualification;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
          name = "employee_treatments",
          joinColumns = @JoinColumn(name = "employee_license_number"),
          inverseJoinColumns = @JoinColumn(name = "treatment_id")
  )
  private Set<Treatment> treatments = new HashSet<>();

  @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Appointment> appointments = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "provider_license_number")
  private UserProvider provider;

  public Employee() {
  }

  public Employee(String licenseNumber, String name, String lastName, String qualification,
                  Set<Treatment> treatments, List<Appointment> appointments, UserProvider provider) {
    this.licenseNumber = licenseNumber;
    this.name = name;
    this.lastName = lastName;
    this.qualification = qualification;
    this.treatments = treatments;
    this.appointments = appointments;
    this.provider = provider;
  }

  // Getters and setters

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

  public Set<Treatment> getTreatments() {
    return treatments;
  }

  public void setTreatments(Set<Treatment> treatments) {
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
