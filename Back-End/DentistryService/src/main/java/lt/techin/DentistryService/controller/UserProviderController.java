package lt.techin.DentistryService.controller;

import jakarta.validation.Valid;
import lt.techin.DentistryService.dto.userprovider.UserProviderMapper;
import lt.techin.DentistryService.dto.userprovider.UserProviderRequestDTO;
import lt.techin.DentistryService.dto.userprovider.UserProviderResponseDTO;
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
import java.util.Optional;

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
            "licenseNumber", "",
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
    if (this.userProviderService.existsByLicenseNumber(userProviderRequestDTO.licenseNumber())) {
      Map<String, String> response = new HashMap<>();
      response.put("message", "Provider with this licence number already exists");

      return ResponseEntity.badRequest().body(response);
    }
    UserProvider userProvider = UserProviderMapper.toUserProvider(userProviderRequestDTO);


    String encodedPassword = passwordEncoder.encode(userProvider.getPassword());
    userProvider.setPassword(encodedPassword);
    userProvider.setLicenseNumber(userProvider.getLicenseNumber());

    UserProvider savedUserProvider = this.userProviderService.saveUserProvider(userProvider);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{licenseNumber}")
                            .buildAndExpand(savedUserProvider.getLicenseNumber())
                            .toUri())
            .body(UserProviderMapper.toProviderDTO(savedUserProvider));

  }


  @PutMapping("/providers/{licenseNumber}")
  public ResponseEntity<Object> updateProvider(
          @PathVariable("licenseNumber") String licenseNumber,
          @Valid @RequestBody UserProviderRequestDTO userProviderRequestDTO) {

    Optional<UserProvider> findUserProvider = this.userProviderService.findByLicenseNumber(licenseNumber);

    if (findUserProvider.isPresent()) {
      UserProvider updateUserProvider = findUserProvider.get();

      updateUserProvider.setName(userProviderRequestDTO.name());
      updateUserProvider.setEmail(userProviderRequestDTO.email());
      updateUserProvider.setPhoneNumber(userProviderRequestDTO.phoneNumber());
      updateUserProvider.setDescription(userProviderRequestDTO.description());
      updateUserProvider.setAddress(userProviderRequestDTO.address());
      updateUserProvider.setContacts(userProviderRequestDTO.contacts());

      UserProvider saved = this.userProviderService.saveUserProvider(updateUserProvider);

      return ResponseEntity.ok(UserProviderMapper.toProviderDTO(saved));
    }

    UserProvider newUserProvider = this.userProviderService.saveUserProvider(UserProviderMapper.toUserProvider(userProviderRequestDTO));

    return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest()
                    .replacePath("/providers/{licenseNumber}")
                    .buildAndExpand(newUserProvider.getLicenseNumber())
                    .toUri()
    ).body(UserProviderMapper.toProviderDTO(newUserProvider));
  }

  @DeleteMapping("/providers/{licenseNumber}")
  public ResponseEntity<Void> deleteUserProvider(@PathVariable("licenseNumber") String licenseNumber) {
    Optional<UserProvider> findUserProvider = this.userProviderService.findByLicenseNumber(licenseNumber);

    if (findUserProvider.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    this.userProviderService.deleteUserProvider(licenseNumber);
    return ResponseEntity.noContent().build();
  }

}
