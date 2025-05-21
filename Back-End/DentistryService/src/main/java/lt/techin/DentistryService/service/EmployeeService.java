package lt.techin.DentistryService.service;

import jakarta.transaction.Transactional;
import lt.techin.DentistryService.dto.employee.EmployeeRequestDTO;
import lt.techin.DentistryService.model.Appointment;
import lt.techin.DentistryService.model.Employee;
import lt.techin.DentistryService.model.Treatment;
import lt.techin.DentistryService.model.UserProvider;
import lt.techin.DentistryService.repository.EmployeeRepository;
import lt.techin.DentistryService.repository.TreatmentRepository;
import lt.techin.DentistryService.repository.UserProviderRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

  private final EmployeeRepository employeeRepository;
  private final UserProviderRepository userProviderRepository;
  private final TreatmentRepository treatmentRepository;

  public EmployeeService(EmployeeRepository employeeRepository, UserProviderRepository userProviderRepository, TreatmentRepository treatmentRepository) {
    this.employeeRepository = employeeRepository;
    this.userProviderRepository = userProviderRepository;
    this.treatmentRepository = treatmentRepository;
  }

  @Transactional
  public Employee toEntity(EmployeeRequestDTO dto) {
    if (dto == null) return null;

    Employee employee = new Employee();
    employee.setLicenseNumber(dto.licenseNumber());
    employee.setName(dto.name());
    employee.setLastName(dto.lastName());
    employee.setQualification(dto.qualification());

    // Fetch Treatment entities by IDs
    Set<Treatment> treatments = (dto.treatmentIds() == null) ? Collections.emptySet() :
            dto.treatmentIds().stream()
                    .map(id -> treatmentRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Treatment not found with id: " + id)))
                    .collect(Collectors.toSet());
    // Map appointments if needed
    if (dto.appointments() != null) {
      List<Appointment> appointments = dto.appointments().stream()
              .map(this::toAppointmentEntity)
              .collect(Collectors.toList());
      appointments.forEach(a -> a.setEmployee(employee));
      employee.setAppointments(appointments);
    }

    return employee;
  }

  private Appointment toAppointmentEntity(EmployeeRequestDTO.AppointmentDTO dto) {
    Appointment appointment = new Appointment();
    appointment.setDate(dto.date());
    appointment.setStartTime(dto.startTime());
    appointment.setEndTime(dto.endTime());
    return appointment;
  }

  public List<Employee> getEmployeesForProvider(String providerLicenseNumber) {
    return employeeRepository.findByProviderLicenseNumber(providerLicenseNumber);
  }

  public Employee addEmployee(String providerLicenseNumber, Employee employee) {
    UserProvider provider = userProviderRepository.findById(providerLicenseNumber)
            .orElseThrow(() -> new RuntimeException("Provider not found"));

    employee.setProvider(provider);
    return employeeRepository.save(employee);
  }

  public Employee updateEmployee(String providerLicenseNumber, String employeeLicenseNumber, Employee updatedEmployee) {
    Employee employee = employeeRepository.findById(employeeLicenseNumber)
            .orElseThrow(() -> new RuntimeException("Employee not found"));

    if (!employee.getProvider().getLicenseNumber().equals(providerLicenseNumber)) {
      throw new RuntimeException("Unauthorized");
    }

    // Update fields
    employee.setName(updatedEmployee.getName());
    employee.setLastName(updatedEmployee.getLastName());
    employee.setQualification(updatedEmployee.getQualification());
    employee.setTreatments(updatedEmployee.getTreatments());
    // Optionally update appointments here or via separate endpoints

    return employeeRepository.save(employee);
  }

  public void deleteEmployee(String providerLicenseNumber, String employeeLicenseNumber) {
    Employee employee = employeeRepository.findById(employeeLicenseNumber)
            .orElseThrow(() -> new RuntimeException("Employee not found"));

    if (!employee.getProvider().getLicenseNumber().equals(providerLicenseNumber)) {
      throw new RuntimeException("Unauthorized");
    }

    employeeRepository.delete(employee);
  }
}
