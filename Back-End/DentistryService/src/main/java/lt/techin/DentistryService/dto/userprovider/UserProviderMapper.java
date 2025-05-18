package lt.techin.DentistryService.dto.userprovider;

import lt.techin.DentistryService.dto.login.ProviderLoginResponseDTO;
import lt.techin.DentistryService.model.Role;
import lt.techin.DentistryService.model.UserProvider;

import java.util.List;

public class UserProviderMapper {

  public static UserProvider toUserProvider(UserProviderRequestDTO userProviderRequestDTO) {
    return new UserProvider(
            userProviderRequestDTO.licenseNumber(),
            userProviderRequestDTO.name(),
            userProviderRequestDTO.email(),
            userProviderRequestDTO.phoneNumber(),
            userProviderRequestDTO.description(),
            userProviderRequestDTO.address(),
            userProviderRequestDTO.contacts(),
            userProviderRequestDTO.username(),
            userProviderRequestDTO.password(),
            userProviderRequestDTO.roles()
    );
  }

  public static UserProviderResponseDTO toProviderDTO(UserProvider userProvider) {
    return new UserProviderResponseDTO(
            userProvider.getLicenseNumber(),
            userProvider.getName(),
            userProvider.getEmail(),
            userProvider.getPhoneNumber(),
            userProvider.getDescription(),
            userProvider.getAddress(),
            userProvider.getContacts(),
            userProvider.getUsername(),
            userProvider.getRoles()
    );
  }

  public static List<UserProviderResponseDTO> toProviderListDTO(List<UserProvider> usersProviders) {
    return usersProviders.stream()
            .map(u -> new UserProviderResponseDTO(
                    u.getLicenseNumber(),
                    u.getName(),
                    u.getEmail(),
                    u.getPhoneNumber(),
                    u.getDescription(),
                    u.getAddress(),
                    u.getContacts(),
                    u.getUsername(),
                    u.getRoles()))
            .toList();
  }

  public static ProviderLoginResponseDTO toLoginProviderResponseDTO(UserProvider userProvider) {
    return new ProviderLoginResponseDTO(
            userProvider.getUsername(),
            userProvider.getRoles().stream().map(Role::getName).toList(),
            userProvider.getLicenseNumber()
    );
  }
}
