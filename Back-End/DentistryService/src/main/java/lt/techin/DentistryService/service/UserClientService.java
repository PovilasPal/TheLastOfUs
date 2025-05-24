package lt.techin.DentistryService.service;

import lt.techin.DentistryService.model.UserClient;
import lt.techin.DentistryService.repository.UserClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

  public List<UserClient> findAllUsers() {
    return this.userClientRepository.findAll();
  }

  public Optional<UserClient> findUserById(long id) {
    return this.userClientRepository.findById(id);
  }

  public Optional<UserClient> findUserClientByUsername(String username) {
    return this.userClientRepository.findByUsername(username);
  }

  public boolean existsUserClientByUsername(String username) {
    return this.userClientRepository.existsByUsername(username);
  }

  public UserClient saveUserClient(UserClient userClient) {
    return this.userClientRepository.save(userClient);
  }

  public void deleteUser(long id) {
    this.userClientRepository.deleteById(id);
  }
}
