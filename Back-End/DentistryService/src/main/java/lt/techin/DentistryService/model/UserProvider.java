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
  private String licenceNumber;

  private String name;
  private String email;
  private String phoneNumber;
  private String username;
  private String password;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
          name = "users_roless",
          joinColumns = @JoinColumn(name = "user_licence_number"),
          inverseJoinColumns = @JoinColumn(name = "roles_id")
  )
  private List<Role> roles;

  public UserProvider(String licenceNumber, String name, String email, String phoneNumber, String username, String password, List<Role> roles) {
    this.licenceNumber = licenceNumber;
    this.name = name;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.username = username;
    this.password = password;
    this.roles = roles;
  }

  public UserProvider() {
  }

  public String getLicenceNumber() {
    return licenceNumber;
  }

  public void setLicenceNumber(String licenceNumber) {
    this.licenceNumber = licenceNumber;
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
