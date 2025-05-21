package lt.techin.DentistryService.dto.employee;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record EmployeeRequestDTO(
        @NotBlank(message = "License number is mandatory")
        String licenseNumber,

        @NotBlank(message = "Name is mandatory")
        String name,

        @NotBlank(message = "Last name is mandatory")
        String lastName,

        @NotBlank(message = "Qualification is mandatory")
        String qualification,

        @NotNull(message = "Treatments list cannot be null")
        @Size(min = 1, message = "At least one treatment is required")
        List<Long> treatmentIds,


        List<@Valid AppointmentDTO> appointments

) {
  public record AppointmentDTO(

          @NotNull(message = "Appointment date is mandatory")
          @FutureOrPresent(message = "Appointment date cannot be in the past")
          LocalDate date,

          @NotNull(message = "Start time is mandatory")
          LocalTime startTime,

          @NotNull(message = "End time is mandatory")
          LocalTime endTime

  ) {
  }
}

