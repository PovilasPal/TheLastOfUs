package lt.techin.DentistryService.dto.appointment;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentRequestDTO(
        @NotNull(message = "Date is mandatory")
        @FutureOrPresent(message = "Date cannot be in the past")
        LocalDate date,

        @NotNull(message = "Start time is mandatory")
        LocalTime startTime,

        @NotNull(message = "End time is mandatory")
        LocalTime endTime) {
}
