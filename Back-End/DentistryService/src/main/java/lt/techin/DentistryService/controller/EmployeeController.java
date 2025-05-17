package lt.techin.DentistryService.controller;

import jakarta.validation.Valid;
import lt.techin.DentistryService.dto.employee.EmployeeRequestDTO;
import lt.techin.DentistryService.dto.employee.EmployeeResponseDTO;
import lt.techin.DentistryService.security.CurrentUserProvider;
import lt.techin.DentistryService.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/userProviders/me/employees")
public class EmployeeController {

  private final EmployeeService employeeService;
  private final CurrentUserProvider currentUserProvider;

  public EmployeeController(EmployeeService employeeService, CurrentUserProvider currentUserProvider) {
    this.employeeService = employeeService;
    this.currentUserProvider = currentUserProvider;
  }

  @GetMapping
  public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() throws AccessDeniedException {
    String currentLicence = currentUserProvider.getCurrentLicenceNumber();
    return ResponseEntity.ok(employeeService.getEmployeesForUserProvider(currentLicence));
  }

  @GetMapping("/{id}")
  public ResponseEntity<EmployeeResponseDTO> getEmployee(@PathVariable Long id) throws AccessDeniedException {
    String currentLicence = currentUserProvider.getCurrentLicenceNumber();
    return ResponseEntity.ok(employeeService.getEmployeeById(currentLicence, id));
  }

  @PostMapping
  public ResponseEntity<EmployeeResponseDTO> createEmployee(
          @Valid @RequestBody EmployeeRequestDTO request) throws AccessDeniedException {

    // Get authenticated provider's licence number
    String providerLicence = currentUserProvider.getCurrentProvider()
            .getLicenceNumber();

    // Create employee tied to this provider
    EmployeeResponseDTO response = employeeService.createEmployee(
            providerLicence,
            request
    );

    // Return with location header
    URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();

    return ResponseEntity.created(location).body(response);
  }


  @PutMapping("/{id}")
  public ResponseEntity<EmployeeResponseDTO> updateEmployee(
          @PathVariable Long id,
          @Valid @RequestBody EmployeeRequestDTO request) throws AccessDeniedException {
    String licenceNumber = currentUserProvider.getCurrentLicenceNumber();
    return ResponseEntity.ok(employeeService.updateEmployee(licenceNumber, id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) throws AccessDeniedException {
    String currentLicence = currentUserProvider.getCurrentLicenceNumber();
    employeeService.deleteEmployee(currentLicence, id);
    return ResponseEntity.noContent().build();
  }

}