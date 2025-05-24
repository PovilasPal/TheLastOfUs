package lt.techin.DentistryService.service;

import lt.techin.DentistryService.dto.appointment.AppointmentMapper;
import lt.techin.DentistryService.dto.appointment.AppointmentRequestDTO;
import lt.techin.DentistryService.dto.employee.EmployeeMapper;
import lt.techin.DentistryService.dto.employee.EmployeeRequestDTO;
import lt.techin.DentistryService.dto.employee.EmployeeResponseDTO;
import lt.techin.DentistryService.model.Appointment;
import lt.techin.DentistryService.model.Employee;
import lt.techin.DentistryService.model.Treatment;
import lt.techin.DentistryService.model.UserProvider;
import lt.techin.DentistryService.repository.EmployeeRepository;
import lt.techin.DentistryService.repository.TreatmentRepository;
import lt.techin.DentistryService.repository.UserProviderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

  private final EmployeeRepository employeeRepository;
  private final UserProviderRepository userProviderRepository;
  private final TreatmentRepository treatmentRepository;


  public EmployeeService(EmployeeRepository employeeRepository, UserProviderRepository userProviderRepository,
                         TreatmentRepository treatmentRepository) {
    this.employeeRepository = employeeRepository;
    this.userProviderRepository = userProviderRepository;
    this.treatmentRepository = treatmentRepository;
  }

  @Transactional
  public Employee saveEmployee(EmployeeRequestDTO dto) {
    Employee employee = toEntity(dto);
    return employeeRepository.save(employee);
  }

  @Transactional
  public Employee toEntity(EmployeeRequestDTO dto) {
    if (dto == null) return null;

    Employee employee = new Employee();
    employee.setLicenseNumber(dto.licenseNumber());
    employee.setName(dto.name());
    employee.setLastName(dto.lastName());
    employee.setQualification(dto.qualification());

    // Treatments mapping (your existing code)
    Set<Treatment> treatments = (dto.treatmentIds() == null) ? Collections.emptySet() :
            dto.treatmentIds().stream()
                    .map(id -> treatmentRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Treatment not found with id: " + id)))
                    .collect(Collectors.toSet());
    employee.setTreatments(treatments);

    // Appointments mapping (rewritten)
    if (dto.appointments() != null) {
      List<Appointment> appointments = dto.appointments().stream()
              .map(empApptDto -> {
                AppointmentRequestDTO apptDto = new AppointmentRequestDTO(
                        empApptDto.date(),
                        empApptDto.startTime(),
                        empApptDto.endTime()
                );
                return AppointmentMapper.toEntity(apptDto);
              })
              .collect(Collectors.toList());

      appointments.forEach(a -> a.setEmployee(employee));
      employee.setAppointments(appointments);
    }

    return employee;
  }


  @Transactional(readOnly = true)
  public List<EmployeeResponseDTO> getEmployeesForProvider(String providerLicenseNumber) {
    List<Employee> employees = employeeRepository.findByProviderLicenseNumberWithDetails(providerLicenseNumber);
    return employees.stream()
            .map(EmployeeMapper::toResponseDTO)
            .collect(Collectors.toList());
  }


  private Appointment toEntity(AppointmentRequestDTO dto) {
    Appointment appointment = new Appointment();
    appointment.setDate(dto.date());
    appointment.setStartTime(dto.startTime());
    appointment.setEndTime(dto.endTime());
    return appointment;
  }

  @Transactional
  public Employee addEmployee(String providerLicenseNumber, Employee employee) {
    UserProvider provider = userProviderRepository.findById(providerLicenseNumber)
            .orElseThrow(() -> new RuntimeException("Provider not found"));

    employee.setProvider(provider);
    return employeeRepository.save(employee);
  }

  @Transactional
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

  @Transactional
  public void deleteEmployee(String providerLicenseNumber, String employeeLicenseNumber) {
    Employee employee = employeeRepository.findById(employeeLicenseNumber)
            .orElseThrow(() -> new RuntimeException("Employee not found"));

    if (!employee.getProvider().getLicenseNumber().equals(providerLicenseNumber)) {
      throw new RuntimeException("Unauthorized");
    }

    employeeRepository.delete(employee);
  }
}
