package lt.techin.DentistryService.dto.userclient;

import lt.techin.DentistryService.model.Role;

import java.util.List;

public record UserClientRequestDTO(
        String name,
        String surname,
        String email,
        String phoneNumber,
        String username,
        String password,
        List<Role> roles
) {
}
