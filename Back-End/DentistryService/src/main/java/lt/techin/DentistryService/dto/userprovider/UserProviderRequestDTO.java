package lt.techin.DentistryService.dto.userprovider;

import lt.techin.DentistryService.model.Role;

import java.util.List;

public record UserProviderRequestDTO(
        String licenceNumber,
        String name,
        String email,
        String phoneNumber,
        String username,
        String password,
        List<Role> roles
) {
}
