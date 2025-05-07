package lt.techin.DentistryService.repository;

import lt.techin.DentistryService.model.UserClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserClientRepository extends JpaRepository<UserClient, Long> {
  Optional<UserClient> findByUsername(String username);


}