package lt.techin.DentistryService.service;

import lt.techin.DentistryService.model.UserProvider;
import lt.techin.DentistryService.repository.UserProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProviderService {

  private final UserProviderRepository userProviderRepository;

  @Autowired
  public UserProviderService(UserProviderRepository userProviderRepository) {
    this.userProviderRepository = userProviderRepository;
  }

  public Optional<UserProvider> findByUsername(String username) {
    return userProviderRepository.findByUsername(username);
  }

  public boolean existUserByUserName(String name) {
    return this.userProviderRepository.existByUserName(name);
  }

  public UserProvider saveUserProvider(UserProvider userProvider) {
    return this.userProviderRepository.save(userProvider);
  }
}
