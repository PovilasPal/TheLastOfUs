package lt.techin.DentistryService.dto.userProvider;

import lt.techin.DentistryService.model.Role;

import java.util.List;

public record UserProviderResponseDTO(
        String licenceNumber,
        String name,
        String email,
        String phoneNumber,
        String username,
        List<Role> roles
) {
}
