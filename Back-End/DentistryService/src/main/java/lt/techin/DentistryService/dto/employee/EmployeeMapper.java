package lt.techin.DentistryService.dto.employee;

import lt.techin.DentistryService.model.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

  public Employee toEntity(EmployeeRequestDTO dto) {
    Employee employee = new Employee();
    updateEntityFromDto(dto, employee); // Reuse the update logic
    return employee;
  }

  public void updateEntityFromDto(EmployeeRequestDTO dto, Employee entity) {
    if (dto.firstName() != null) {
      entity.setFirstName(dto.firstName());
    }

    if (dto.lastName() != null) {
      entity.setLastName(dto.lastName());
    }

    if (dto.licenceNumber() != null) {
      entity.setLicenceNumber(dto.licenceNumber());
    }

    // Handle nullable fields with defaults
    entity.setQualification(dto.qualification() != null ? dto.qualification() : "");
    entity.setService(dto.service() != null ? dto.service() : "");

    if (dto.appointment() != null) {
      entity.setAppointment(dto.appointment());
    }
  }

  public EmployeeResponseDTO toResponseDTO(Employee employee) {
    return new EmployeeResponseDTO(
            employee.getId(),
            employee.getFirstName(),
            employee.getLastName(),
            employee.getLicenceNumber(),
            employee.getQualification(),
            employee.getService(),
            employee.getAppointment()
    );
  }


}