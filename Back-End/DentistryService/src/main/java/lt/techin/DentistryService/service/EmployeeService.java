package lt.techin.DentistryService.service;

import lt.techin.DentistryService.dto.employee.EmployeeMapper;
import lt.techin.DentistryService.dto.employee.EmployeeRequestDTO;
import lt.techin.DentistryService.dto.employee.EmployeeResponseDTO;
import lt.techin.DentistryService.model.Employee;
import lt.techin.DentistryService.model.UserProvider;
import lt.techin.DentistryService.repository.EmployeeRepository;
import lt.techin.DentistryService.repository.UserProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EmployeeService {
  private final EmployeeRepository employeeRepository;
  private final EmployeeMapper employeeMapper;
  private final UserProviderRepository userProviderRepository;

  @Autowired
  public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, UserProviderRepository userProviderRepository) {

    this.employeeRepository = employeeRepository;
    this.employeeMapper = employeeMapper;
    this.userProviderRepository = userProviderRepository;

  }

  public List<EmployeeResponseDTO> getEmployeesForUserProvider(String providerLicenceNumber) {
    return employeeRepository.findByUserProviderLicenceNumberAndIsActiveTrue(providerLicenceNumber)
            .stream()
            .map(employeeMapper::toResponseDTO)
            .toList();
  }

  public EmployeeResponseDTO getEmployeeById(String providerLicenceNumber, Long employeeId) {
    Employee employee = employeeRepository
            .findByIdAndUserProviderLicenceNumber(employeeId, providerLicenceNumber)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Employee not found or doesn't belong to provider with licence: " + providerLicenceNumber
            ));

    return employeeMapper.toResponseDTO(employee);
  }

  public EmployeeResponseDTO updateEmployee(String providerLicence, Long employeeId, EmployeeRequestDTO request) {
    // 1. Verify provider exists
    UserProvider provider = userProviderRepository.findByLicenceNumber(providerLicence)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider not found with licence: " + providerLicence));

    // 2. Verify employee exists and belongs to this provider
    Employee employee = employeeRepository.findByIdAndUserProvider(employeeId, provider)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Employee not found or doesn't belong to provider"
            ));
    employee.setFirstName(request.firstName());
    employee.setLastName(request.lastName());
    employee.setLicenceNumber(request.licenceNumber());
    employee.setQualification(request.qualification() != null ? request.qualification() : "");
    employee.setService(request.service() != null ? request.service() : "");

    // 4. Save and return updated employee
    Employee updatedEmployee = employeeRepository.save(employee);
    return employeeMapper.toResponseDTO(updatedEmployee);
  }

  public void deleteEmployee(String providerLicenceNumber, Long employeeId) {
    // 1. Find employee by ID and verify it belongs to the provider
    Employee employee = employeeRepository
            .findByIdAndUserProviderLicenceNumber(employeeId, providerLicenceNumber)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Employee not found or doesn't belong to provider with licence: " + providerLicenceNumber
            ));

    // 2. Soft delete (set active to false)
    employee.setActive(false);

    // 3. Save changes
    employeeRepository.save(employee);
  }

  public EmployeeResponseDTO createEmployee(String providerLicence, EmployeeRequestDTO request) {
    // Verify provider exists
    UserProvider provider = userProviderRepository.findByLicenceNumber(providerLicence)
            .orElseThrow(() -> new ProviderNotFoundException(providerLicence));

    // Check for duplicate employee licence
    if (employeeRepository.existsByLicenceNumber(request.licenceNumber())) {
      throw new EmployeeLicenceConflictException(request.licenceNumber());
    }

    // Create and save employee
    Employee employee = employeeMapper.toEntity(request);
    employee.setUserProvider(provider);

    return employeeMapper.toResponseDTO(employeeRepository.save(employee));
  }

  // Exception classes
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public static class ProviderNotFoundException extends RuntimeException {
    public ProviderNotFoundException(String licence) {
      super("Provider not found with licence: " + licence);
    }
  }

  @ResponseStatus(HttpStatus.CONFLICT)
  public static class EmployeeLicenceConflictException extends RuntimeException {
    public EmployeeLicenceConflictException(String licence) {
      super("Employee licence already exists: " + licence);
    }
  }
}
