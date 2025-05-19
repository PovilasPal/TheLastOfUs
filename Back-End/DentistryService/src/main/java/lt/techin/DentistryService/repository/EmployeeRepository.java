package lt.techin.DentistryService.repository;

import lt.techin.DentistryService.model.Employee;
import lt.techin.DentistryService.model.UserProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
  Optional<Employee> findByIdAndUserProviderLicenseNumber(Long id, String licenceNumber);


  List<Employee> findByUserProviderLicenseNumberAndIsActiveTrue(String licenseNumber);

  boolean existsByLicenseNumber(String licenceNumber);

  Optional<Employee> findByIdAndUserProvider(Long employeeId, UserProvider provider);


}