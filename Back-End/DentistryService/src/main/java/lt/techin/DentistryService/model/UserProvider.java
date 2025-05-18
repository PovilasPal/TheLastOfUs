package lt.techin.DentistryService.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users_providers")
public class UserProvider implements UserDetails {

  @Id
  private String licenseNumber;

  private String name;
  private String email;
  private String phoneNumber;
  private String description;
  private String address;
  private String contacts;
  private String username;
  private String password;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
          name = "providers_roles",
          joinColumns = @JoinColumn(name = "user_license_number"),
          inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private List<Role> roles;

  public UserProvider(String licenseNumber, String name, String email, String phoneNumber, String description, String address, String contacts, String username, String password, List<Role> roles) {
    this.licenseNumber = licenseNumber;
    this.name = name;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.description = description;
    this.address = address;
    this.contacts = contacts;
    this.username = username;
    this.password = password;
    this.roles = roles;
  }

  public UserProvider() {
  }

  public String getLicenseNumber() {
    return licenseNumber;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getContacts() {
    return contacts;
  }

  public void setContacts(String contacts) {
    this.contacts = contacts;
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  @Override
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles;
  }
}
