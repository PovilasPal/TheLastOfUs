package lt.techin.DentistryService.dto.employee;

import lt.techin.DentistryService.model.Appointment;
import lt.techin.DentistryService.model.Employee;
import lt.techin.DentistryService.model.Treatment;

import java.util.List;
import java.util.stream.Collectors;


public class EmployeeMapper {

  public static EmployeeResponseDTO toResponseDTO(Employee employee) {
    if (employee == null) return null;

    List<EmployeeResponseDTO.AppointmentDTO> appointments = employee.getAppointments().stream()
            .map(EmployeeMapper::toAppointmentDTO)
            .collect(Collectors.toList());

    List<String> treatmentNames = employee.getTreatments().stream()
            .map(Treatment::getName)
            .collect(Collectors.toList());

    return new EmployeeResponseDTO(
            employee.getLicenseNumber(),
            employee.getName(),
            employee.getLastName(),
            employee.getQualification(),
            treatmentNames,
            appointments
    );
  }

  private static EmployeeResponseDTO.AppointmentDTO toAppointmentDTO(Appointment appointment) {
    return new EmployeeResponseDTO.AppointmentDTO(
            appointment.getDate(),
            appointment.getStartTime(),
            appointment.getEndTime()
    );
  }

  private Appointment toAppointmentEntity(EmployeeRequestDTO.AppointmentDTO dto) {
    Appointment appointment = new Appointment();
    appointment.setDate(dto.date());
    appointment.setStartTime(dto.startTime());
    appointment.setEndTime(dto.endTime());
    return appointment;
  }
}