package lt.techin.DentistryService.controller;

import jakarta.validation.Valid;
import lt.techin.DentistryService.dto.employee.EmployeeMapper;
import lt.techin.DentistryService.dto.employee.EmployeeRequestDTO;
import lt.techin.DentistryService.dto.employee.EmployeeResponseDTO;
import lt.techin.DentistryService.model.Employee;
import lt.techin.DentistryService.model.Treatment;
import lt.techin.DentistryService.model.UserProvider;
import lt.techin.DentistryService.service.EmployeeService;
import lt.techin.DentistryService.service.TreatmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/provider/employees")
public class EmployeeController {

  private final EmployeeService employeeService;
  private final TreatmentService treatmentService;

  public EmployeeController(EmployeeService employeeService, TreatmentService treatmentService) {
    this.employeeService = employeeService;
    this.treatmentService = treatmentService;
  }

  // Get all employees for the logged-in provider
  @GetMapping
  public ResponseEntity<List<EmployeeResponseDTO>> getEmployees(Authentication authentication) {
    UserProvider provider = (UserProvider) authentication.getPrincipal();
    List<Employee> employees = employeeService.getEmployeesForProvider(provider.getLicenseNumber());

    List<EmployeeResponseDTO> employeeDTOs = employees.stream()
            .map(EmployeeMapper::toResponseDTO)
            .collect(Collectors.toList());

    return ResponseEntity.ok(employeeDTOs);
  }

  // Add a new employee for the logged-in provider
  @PostMapping
  public ResponseEntity<EmployeeResponseDTO> addEmployee(
          Authentication authentication,
          @Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {

    UserProvider provider = (UserProvider) authentication.getPrincipal();

    Employee employee = employeeService.toEntity(employeeRequestDTO);
    Employee savedEmployee = employeeService.addEmployee(provider.getLicenseNumber(), employee);

    EmployeeResponseDTO responseDTO = EmployeeMapper.toResponseDTO(savedEmployee);

    return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
  }

  // Update an existing employee by license number
  @PutMapping("/{licenseNumber}")
  public ResponseEntity<EmployeeResponseDTO> updateEmployee(
          Authentication authentication,
          @PathVariable String licenseNumber,
          @Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {

    UserProvider provider = (UserProvider) authentication.getPrincipal();

    Employee updatedEntity = employeeService.toEntity(employeeRequestDTO);
    Employee updatedEmployee = employeeService.updateEmployee(provider.getLicenseNumber(), licenseNumber, updatedEntity);

    EmployeeResponseDTO responseDTO = EmployeeMapper.toResponseDTO(updatedEmployee);

    return ResponseEntity.ok(responseDTO);
  }

  @GetMapping("/treatments")
  public ResponseEntity<List<Treatment>> getAllTreatments() {
    try {
      List<Treatment> treatments = treatmentService.getAllTreatments();
      return ResponseEntity.ok(treatments);
    } catch (Exception e) {
      e.printStackTrace(); // Log full stack trace
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

  }

  // Delete an employee by license number
  @DeleteMapping("/{licenseNumber}")
  public ResponseEntity<Void> deleteEmployee(
          Authentication authentication,
          @PathVariable String licenseNumber) {

    UserProvider provider = (UserProvider) authentication.getPrincipal();

    employeeService.deleteEmployee(provider.getLicenseNumber(), licenseNumber);

    return ResponseEntity.noContent().build();
  }
}


