package lt.techin.DentistryService.dto.employee;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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


        List<EmployeeResponseDTO.AppointmentDTO> appointments) {
}



