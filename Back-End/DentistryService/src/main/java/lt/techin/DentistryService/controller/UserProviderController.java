package lt.techin.DentistryService.controller;

import jakarta.validation.Valid;
import lt.techin.DentistryService.dto.userProvider.UserProviderMapper;
import lt.techin.DentistryService.dto.userProvider.UserProviderResponseDTO;
import lt.techin.DentistryService.dto.userprovider.UserProviderRequestDTO;
import lt.techin.DentistryService.model.UserProvider;
import lt.techin.DentistryService.service.UserProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserProviderController {
  private final UserProviderService userProviderService;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  public UserProviderController(UserProviderService userProviderService) {
    this.userProviderService = userProviderService;
  }

  @GetMapping("/providers/{licenceNumber}")
  public ResponseEntity<UserProviderResponseDTO> getProviderByLicenceNumber(
          @PathVariable String licenceNumber) {
    return userProviderService.findByLicenceNumber(licenceNumber)
            .map(UserProviderMapper::toProviderDTO)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/providers")
  public ResponseEntity<List<UserProviderResponseDTO>> getAllProviders() {
    List<UserProviderResponseDTO> providers = userProviderService.findAllProviders()
            .stream()
            .map(UserProviderMapper::toProviderDTO)
            .toList();
    return ResponseEntity.ok(providers);
  }

  @GetMapping("/register")
  public Map<String, Object> getRegistrationFormStructure() {
    return Map.of(
            "licenceNumber", "",
            "name", "",
            "email", "",
            "phoneNumber", "",
            "username", "",
            "password", "",
            "roles", List.of(Map.of("id", 2, "name", "PROVIDER"))
    );
  }

  @PostMapping("/providerRegistration")
  public ResponseEntity<Object> saveUserProvider(
          @Valid @RequestBody UserProviderRequestDTO userProviderRequestDTO) {
    if (this.userProviderService.existsByLicenceNumber(userProviderRequestDTO.licenceNumber())) {
      Map<String, String> response = new HashMap<>();
      response.put("message", "Provider with this licence number already exists");

      return ResponseEntity.badRequest().body(response);
    }
    UserProvider userProvider = UserProviderMapper.toUserProvider(userProviderRequestDTO);

    String encodedPassword = passwordEncoder.encode(userProvider.getPassword());
    userProvider.setPassword(encodedPassword);

    UserProvider savedUserProvider = this.userProviderService.saveUserProvider(userProvider);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedUserProvider.getLicenceNumber())
                            .toUri())
            .body(UserProviderMapper.toProviderDTO(savedUserProvider));

  }

}
