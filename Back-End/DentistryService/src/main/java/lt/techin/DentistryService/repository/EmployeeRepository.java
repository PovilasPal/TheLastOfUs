package lt.techin.DentistryService.repository;

import lt.techin.DentistryService.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
  List<Employee> findByProviderLicenseNumber(String licenseNumber);

  @Query("SELECT DISTINCT e FROM Employee e " +
          "LEFT JOIN FETCH e.treatments " +
          "LEFT JOIN FETCH e.appointments " +
          "WHERE e.provider.licenseNumber = :providerLicenseNumber")
  List<Employee> findByProviderLicenseNumberWithDetails(@Param("providerLicenseNumber") String providerLicenseNumber);
}
