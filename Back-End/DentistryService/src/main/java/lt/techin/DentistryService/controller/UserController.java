package lt.techin.DentistryService.controller;

import lt.techin.DentistryService.dto.login.ProviderLoginResponseDTO;
import lt.techin.DentistryService.dto.login.UserLoginResponseDTO;
import lt.techin.DentistryService.dto.userclient.UserClientMapper;
import lt.techin.DentistryService.dto.userprovider.UserProviderMapper;
import lt.techin.DentistryService.dto.userprovider.UserProviderResponseDTO;
import lt.techin.DentistryService.model.UserClient;
import lt.techin.DentistryService.model.UserProvider;
import lt.techin.DentistryService.service.UserProviderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

  private final UserProviderService userProviderService;

  public UserController(UserProviderService userProviderService) {
    this.userProviderService = userProviderService;
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
}
