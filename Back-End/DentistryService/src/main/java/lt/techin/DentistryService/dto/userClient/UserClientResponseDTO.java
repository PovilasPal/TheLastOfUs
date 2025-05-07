package lt.techin.DentistryService.dto.userClient;

import lt.techin.DentistryService.model.Role;

import java.util.List;

public record UserClientResponseDTO(
        long id,
        String name,
        String surname,
        String email,
        String phoneNumber,
        String username,
        List<Role> roles) {
}
