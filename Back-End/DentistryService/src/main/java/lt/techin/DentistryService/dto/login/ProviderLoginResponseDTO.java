package lt.techin.DentistryService.dto.login;

import java.util.List;


public record ProviderLoginResponseDTO(String username, List<String> roles, String licenseNumber) {
}


