package lt.techin.DentistryService.controller;

import jakarta.validation.Valid;
import lt.techin.DentistryService.dto.login.ProviderLoginResponseDTO;
import lt.techin.DentistryService.dto.login.UserLoginResponseDTO;
import lt.techin.DentistryService.dto.userclient.UserClientMapper;
import lt.techin.DentistryService.dto.userclient.UserClientRequestDTO;
import lt.techin.DentistryService.dto.userprovider.UserProviderMapper;
import lt.techin.DentistryService.dto.userprovider.UserProviderResponseDTO;
import lt.techin.DentistryService.model.UserClient;
import lt.techin.DentistryService.model.UserProvider;
import lt.techin.DentistryService.service.UserClientService;
import lt.techin.DentistryService.service.UserProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

  private final UserProviderService userProviderService;
  private final UserClientService userClientService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public UserController(UserProviderService userProviderService, UserClientService userClientService) {
    this.userProviderService = userProviderService;
    this.userClientService = userClientService;
  }

  @GetMapping("/login/client")
  public ResponseEntity<UserLoginResponseDTO> client(Authentication authentication) {
    UserClient userClient = (UserClient) authentication.getPrincipal();

    return ResponseEntity.ok(UserClientMapper.toLoginClientResponseDTO(userClient));
  }

  @GetMapping("/login/provider")
  public ResponseEntity<ProviderLoginResponseDTO> provider(Authentication authentication) {
    UserProvider userProvider = (UserProvider) authentication.getPrincipal();

    return ResponseEntity.ok(UserProviderMapper.toLoginProviderResponseDTO(userProvider));
  }

  @GetMapping("/provider/search")
  public ResponseEntity<List<UserProviderResponseDTO>> searchByAddress(
          @RequestParam(required = false) String address) {
    List<UserProvider> result = (address == null || address.isBlank())
            ? userProviderService.findAllProviders()
            : userProviderService.searchByAddress(address);

    List<UserProviderResponseDTO> providers = result.stream()
            .map(UserProviderMapper::toProviderDTO)
            .toList();

    return ResponseEntity.ok(providers);
  }

  @PutMapping("/client/{id}")
  public ResponseEntity<Object> updateClient(
          @PathVariable("id") long id,
          @Valid @RequestBody UserClientRequestDTO userClientRequestDTO) {

    Optional<UserClient> findUserClient = this.userClientService.findUserById(id);

    if (findUserClient.isPresent()) {
      UserClient updateUserClient = findUserClient.get();

      updateUserClient.setName(userClientRequestDTO.name());
      updateUserClient.setSurname(userClientRequestDTO.surname());
      updateUserClient.setEmail(userClientRequestDTO.email());
      updateUserClient.setPhoneNumber(userClientRequestDTO.phoneNumber());
      updateUserClient.setUsername(userClientRequestDTO.username());
      if (userClientRequestDTO.password() != null && !userClientRequestDTO.password().isBlank()) {
        updateUserClient.setPassword(passwordEncoder.encode(userClientRequestDTO.password()));
      }

      UserClient saved = this.userClientService.saveUserClient(updateUserClient);

      return ResponseEntity.ok(UserClientMapper.toClientDTO(saved));
    }

    UserClient newUserClient = this.userClientService.saveUserClient(UserClientMapper.toUserClient(userClientRequestDTO));

    return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest()
                    .replacePath("/client/{id}")
                    .buildAndExpand(newUserClient.getId())
                    .toUri()
    ).body(UserClientMapper.toClientDTO(newUserClient));
  }
}
