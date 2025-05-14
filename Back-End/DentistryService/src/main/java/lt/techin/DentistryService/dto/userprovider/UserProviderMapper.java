package lt.techin.DentistryService.dto.userProvider;

import lt.techin.DentistryService.dto.login.LoginResponseDTO;
import lt.techin.DentistryService.model.Role;
import lt.techin.DentistryService.model.UserProvider;

import java.util.List;

public class UserProviderMapper {

  public static UserProvider toUserProvider(UserProviderRequestDTO userProviderRequestDTO) {
    return new UserProvider(
            userProviderRequestDTO.licenceNumber(),
            userProviderRequestDTO.name(),
            userProviderRequestDTO.email(),
            userProviderRequestDTO.phoneNumber(),
            userProviderRequestDTO.username(),
            userProviderRequestDTO.password(),
            userProviderRequestDTO.roles()
    );
  }

  public static UserProviderResponseDTO toProviderDTO(UserProvider userProvider) {
    return new UserProviderResponseDTO(
            userProvider.getLicenceNumber(),
            userProvider.getName(),
            userProvider.getEmail(),
            userProvider.getPhoneNumber(),
            userProvider.getUsername(),
            userProvider.getRoles()
    );
  }

  public static List<UserProviderResponseDTO> toProviderListDTO(List<UserProvider> usersProviders) {
    return usersProviders.stream()
            .map(u -> new UserProviderResponseDTO(
                    u.getLicenceNumber(),
                    u.getName(),
                    u.getEmail(),
                    u.getPhoneNumber(),
                    u.getUsername(),
                    u.getRoles()))
            .toList();
  }

  public static LoginResponseDTO toLoginProviderResponseDTO(UserProvider userProvider) {
    return new LoginResponseDTO(
            userProvider.getUsername(),
            userProvider.getRoles()
                    .stream()
                    .map(Role::getName)
                    .toList()
    );
  }
}
