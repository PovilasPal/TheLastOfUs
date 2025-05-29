package lt.techin.DentistryService.repository;

import lt.techin.DentistryService.model.Treatment;
import lt.techin.DentistryService.model.UserProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserProviderRepository extends JpaRepository<UserProvider, String> {
  Optional<UserProvider> findByUsername(String username);

  List<UserProvider> findAllByTreatmentsContaining(Treatment treatment);

  boolean existsByLicenseNumber(String licenseNumber);

  boolean existsByName(String name);

  boolean existsByUsername(String username);

  @Query("SELECT u FROM UserProvider u WHERE LOWER(u.address) LIKE LOWER(CONCAT('%', :address, '%'))")
  List<UserProvider> searchByAddress(@Param("address") String address);
}
