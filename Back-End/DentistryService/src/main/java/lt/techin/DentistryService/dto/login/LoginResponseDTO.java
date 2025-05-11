package lt.techin.DentistryService.dto.login;

import java.util.List;

public record LoginResponseDTO(String username, List<String> roles) {
}
