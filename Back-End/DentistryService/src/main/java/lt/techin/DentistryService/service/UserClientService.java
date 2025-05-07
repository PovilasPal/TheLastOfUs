package lt.techin.DentistryService.service;

import lt.techin.DentistryService.model.UserClient;
import lt.techin.DentistryService.repository.UserClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserClientService {

  private final UserClientRepository userClientRepository;

  @Autowired
  public UserClientService(UserClientRepository userClientRepository) {
    this.userClientRepository = userClientRepository;
  }

  public Optional<UserClient> findByUsername(String username) {
    return userClientRepository.findByUsername(username);
  }
}
