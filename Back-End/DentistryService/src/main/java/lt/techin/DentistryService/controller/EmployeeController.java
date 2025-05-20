package lt.techin.DentistryService.controller;

import lt.techin.DentistryService.model.Employee;
import lt.techin.DentistryService.model.UserProvider;
import lt.techin.DentistryService.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/provider/employees")
public class EmployeeController {

  private final EmployeeService employeeService;

  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  // Get all employees for logged-in provider
  @GetMapping
  public ResponseEntity<List<Employee>> getEmployees(Authentication authentication) {
    UserProvider provider = (UserProvider) authentication.getPrincipal();
    List<Employee> employees = employeeService.getEmployeesForProvider(provider.getLicenseNumber());
    return ResponseEntity.ok(employees);
  }

  // Add new employee
  @PostMapping
  public ResponseEntity<Employee> addEmployee(Authentication authentication, @RequestBody Employee employee) {
    UserProvider provider = (UserProvider) authentication.getPrincipal();
    Employee savedEmployee = employeeService.addEmployee(provider.getLicenseNumber(), employee);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
  }

  // Update employee
  @PutMapping("/{licenseNumber}")
  public ResponseEntity<Employee> updateEmployee(
          Authentication authentication,
          @PathVariable String licenseNumber,
          @RequestBody Employee employee) {
    UserProvider provider = (UserProvider) authentication.getPrincipal();
    Employee updatedEmployee = employeeService.updateEmployee(provider.getLicenseNumber(), licenseNumber, employee);
    return ResponseEntity.ok(updatedEmployee);
  }

  // Delete employee
  @DeleteMapping("/{licenseNumber}")
  public ResponseEntity<Void> deleteEmployee(Authentication authentication, @PathVariable String licenseNumber) {
    UserProvider provider = (UserProvider) authentication.getPrincipal();
    employeeService.deleteEmployee(provider.getLicenseNumber(), licenseNumber);
    return ResponseEntity.noContent().build();
  }
}

