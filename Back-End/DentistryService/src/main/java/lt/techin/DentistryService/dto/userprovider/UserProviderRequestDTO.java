package lt.techin.DentistryService.dto.userprovider;

import lt.techin.DentistryService.model.Role;
import lt.techin.DentistryService.model.Treatment;

import java.util.List;

public record UserProviderRequestDTO(
        String licenseNumber,
        String name,
        String email,
        String phoneNumber,
        String description,
        String address,
        String contacts,
        String username,
        String password,
        List<Role> roles,
        List<Treatment> treatments
) {
}
