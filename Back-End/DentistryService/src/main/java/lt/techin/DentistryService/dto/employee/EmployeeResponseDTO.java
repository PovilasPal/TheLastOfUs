package lt.techin.DentistryService.dto.employee;

import java.time.LocalDateTime;

public record EmployeeResponseDTO(Long id,
                                  String firstName,
                                  String lastName,
                                  String licenceNumber,
                                  String qualification,
                                  String service,
                                  LocalDateTime appointment
) {
}
