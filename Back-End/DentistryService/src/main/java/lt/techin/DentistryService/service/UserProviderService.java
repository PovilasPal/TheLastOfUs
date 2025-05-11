package lt.techin.DentistryService.service;

import jakarta.validation.constraints.NotNull;
import lt.techin.DentistryService.model.UserProvider;
import lt.techin.DentistryService.repository.UserProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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

  public UserProvider saveUserProvider(UserProvider userProvider) {

    if (userProviderRepository.existsByUsername(userProvider.getUsername())) {
      throw new DuplicateKeyException("Username already exists");
    }
    return userProviderRepository.save(userProvider);
  }

  public boolean existsByName(@NotNull String name) {
    return this.userProviderRepository.existsByName(name);
  }

}

