package lt.techin.DentistryService.dto.userclient;

import lt.techin.DentistryService.dto.login.UserLoginResponseDTO;
import lt.techin.DentistryService.model.Role;
import lt.techin.DentistryService.model.UserClient;

import java.util.List;

public class UserClientMapper {

  public static UserClient toUserClient(UserClientRequestDTO userClientRequestDTO) {
    return new UserClient(
            userClientRequestDTO.name(),
            userClientRequestDTO.surname(),
            userClientRequestDTO.email(),
            userClientRequestDTO.phoneNumber(),
            userClientRequestDTO.username(),
            userClientRequestDTO.password(),
            userClientRequestDTO.roles()
    );
  }

  public static UserClientResponseDTO toClientDTO(UserClient userClient) {
    return new UserClientResponseDTO(
            userClient.getId(),
            userClient.getName(),
            userClient.getSurname(),
            userClient.getEmail(),
            userClient.getPhoneNumber(),
            userClient.getUsername(),
            userClient.getRoles()
    );
  }

  public static List<UserClientResponseDTO> toClientListDTO(List<UserClient> usersClients) {
    return usersClients.stream()
            .map(u -> new UserClientResponseDTO(
                    u.getId(),
                    u.getName(),
                    u.getSurname(),
                    u.getEmail(),
                    u.getPhoneNumber(),
                    u.getUsername(),
                    u.getRoles()))
            .toList();
  }

  public static UserLoginResponseDTO toLoginClientResponseDTO(UserClient userClient) {
    return new UserLoginResponseDTO(
            userClient.getId(),
            userClient.getUsername(),
            userClient.getRoles()
                    .stream()
                    .map(Role::getName)
                    .toList()
    );
  }
}
