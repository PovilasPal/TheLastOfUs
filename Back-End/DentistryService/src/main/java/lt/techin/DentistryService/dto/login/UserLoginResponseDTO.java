package lt.techin.DentistryService.dto.login;

import java.util.List;

public record UserLoginResponseDTO(String username, List<String> roles) {
}
