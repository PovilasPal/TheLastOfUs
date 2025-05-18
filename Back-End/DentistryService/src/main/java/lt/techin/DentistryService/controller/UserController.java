package lt.techin.DentistryService.controller;

import lt.techin.DentistryService.dto.login.ProviderLoginResponseDTO;
import lt.techin.DentistryService.dto.login.UserLoginResponseDTO;
import lt.techin.DentistryService.dto.userclient.UserClientMapper;
import lt.techin.DentistryService.dto.userprovider.UserProviderMapper;
import lt.techin.DentistryService.model.UserClient;
import lt.techin.DentistryService.model.UserProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

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
}
