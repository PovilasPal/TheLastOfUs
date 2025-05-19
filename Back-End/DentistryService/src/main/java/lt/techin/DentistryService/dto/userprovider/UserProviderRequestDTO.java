package lt.techin.DentistryService.dto.userProvider;

import lt.techin.DentistryService.model.Role;

import java.util.List;

public record UserProviderRequestDTO(
        String licenseNumber,
        String name,
        String email,
        String phoneNumber,
        String username,
        String password,
        List<Role> roles
) {
}
