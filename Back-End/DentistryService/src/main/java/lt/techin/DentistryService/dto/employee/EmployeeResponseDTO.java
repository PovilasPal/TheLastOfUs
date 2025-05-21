package lt.techin.DentistryService.dto.employee;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record EmployeeResponseDTO(
        String licenseNumber,
        String name,
        String lastName,
        String qualification,
        List<String> treatments,
        List<AppointmentDTO> appointments
) {
  public record AppointmentDTO(
          LocalDate date,
          LocalTime startTime,
          LocalTime endTime
  ) {
  }
}

