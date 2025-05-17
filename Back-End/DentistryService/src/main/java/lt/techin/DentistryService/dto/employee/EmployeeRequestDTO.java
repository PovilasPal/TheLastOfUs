package lt.techin.DentistryService.dto.employee;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record EmployeeRequestDTO(@NotBlank String firstName,
                                 @NotBlank String lastName,
                                 @NotBlank @Email String licenceNumber,
                                 String qualification,
                                 String service,
                                 LocalDateTime appointment
) {
}
