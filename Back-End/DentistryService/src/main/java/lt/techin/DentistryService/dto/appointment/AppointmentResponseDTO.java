package lt.techin.DentistryService.dto.appointment;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentResponseDTO(
        Long id,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime) {
}
