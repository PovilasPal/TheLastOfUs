package lt.techin.DentistryService.repository;

import lt.techin.DentistryService.model.UserProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProviderRepository extends JpaRepository<UserProvider, String> {
  Optional<UserProvider> findByUsername(String username);


  boolean existsByUsername(String username);

  boolean existsByName(String name);
}
