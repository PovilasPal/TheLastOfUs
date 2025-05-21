package lt.techin.DentistryService.repository;

import lt.techin.DentistryService.model.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
  Optional<Treatment> findByNameIgnoreCase(String name);
}

