package lt.techin.DentistryService.controller;

import jakarta.validation.Valid;
import lt.techin.DentistryService.dto.userProvider.UserProviderMapper;
import lt.techin.DentistryService.dto.userProvider.UserProviderRequestDTO;
import lt.techin.DentistryService.dto.userProvider.UserProviderResponseDTO;
import lt.techin.DentistryService.model.UserProvider;
import lt.techin.DentistryService.service.UserProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

  @GetMapping("/providers/{licenseNumber}")
  public ResponseEntity<UserProviderResponseDTO> getProviderByLicenseNumber(
          @PathVariable String licenseNumber) {
    return userProviderService.findByLicenseNumber(licenseNumber)
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

    if (userProviderService.existsByLicenseNumber(userProviderRequestDTO.licenseNumber())) {
      return ResponseEntity.badRequest()
              .body(Map.of("message", "Provider with this licence number already exists"));
    }

    UserProvider userProvider = UserProviderMapper.toUserProvider(userProviderRequestDTO);

    UserProvider savedUserProvider = userProviderService.saveUserProvider(userProvider);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedUserProvider.getLicenseNumber())
            .toUri();

    return ResponseEntity.created(location)
            .body(UserProviderMapper.toProviderDTO(savedUserProvider));
  }

}
