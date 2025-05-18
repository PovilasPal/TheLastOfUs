package lt.techin.DentistryService.service;

import lt.techin.DentistryService.model.Role;
import lt.techin.DentistryService.model.UserProvider;
import lt.techin.DentistryService.repository.RoleRepository;
import lt.techin.DentistryService.repository.UserProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserProviderService {

  private final UserProviderRepository userProviderRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserProviderService(UserProviderRepository userProviderRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
    this.userProviderRepository = userProviderRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public Optional<UserProvider> findByUsername(String username) {
    return userProviderRepository.findByUsername(username);
  }

  public UserProvider saveUserProvider(UserProvider userProvider) {
    if (userProviderRepository.existsByUsername(userProvider.getUsername())) {
      throw new DuplicateKeyException("Username already exists");
    }

    // Encode password here
    String encodedPassword = passwordEncoder.encode(userProvider.getPassword());
    userProvider.setPassword(encodedPassword);

    Role providerRole = roleRepository.findByName("ROLE_PROVIDER");
    userProvider.setRoles(new ArrayList<>(Set.of(providerRole)));

    return userProviderRepository.save(userProvider);
  }

  public boolean existsByLicenceNumber(String licenceNumber) {
    return this.userProviderRepository.existsByLicenceNumber(licenceNumber);
  }

  public Optional<UserProvider> findByLicenceNumber(String licenceNumber) {
    return userProviderRepository.findById(licenceNumber);
  }

  public List<UserProvider> findAllProviders() {
    return userProviderRepository.findAll();
  }


}



