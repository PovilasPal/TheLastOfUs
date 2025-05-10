package lt.techin.DentistryService.dto.userProvider;

public record UserProviderResponseDTO(
        String licenceNumber,
        String name,
        String email,
        String phoneNumber,
        String username,
        // List<Role> roles
        java.util.List<lt.techin.DentistryService.model.Role> roles) {
}
