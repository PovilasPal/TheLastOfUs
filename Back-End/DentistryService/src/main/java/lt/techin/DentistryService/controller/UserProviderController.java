package lt.techin.DentistryService.controller;

import jakarta.validation.Valid;
import lt.techin.DentistryService.dto.userProvider.UserProviderMapper;
import lt.techin.DentistryService.dto.userProvider.UserProviderRequestDTO;
import lt.techin.DentistryService.model.UserProvider;
import lt.techin.DentistryService.service.UserProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserProviderController {
  private final UserProviderService userProviderService;

  @Autowired
  public UserProviderController(UserProviderService userProviderService) {
    this.userProviderService = userProviderService;
  }


  @PostMapping("/providerRegistration")
  public ResponseEntity<Object> saveUserProvider(
          @Valid @RequestBody UserProviderRequestDTO userProviderRequestDTO) {
    if (this.userProviderService.existsByName(userProviderRequestDTO.name())) {
      Map<String, String> response = new HashMap<>();
      response.put("message", "Provider with this name already exists");
      return ResponseEntity.badRequest().body(response);
    }
    UserProvider savedUserProvider = this.userProviderService.saveUserProvider(UserProviderMapper.toUserProvider(userProviderRequestDTO));


    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedUserProvider.getLicenceNumber())
                            .toUri())
            .body(savedUserProvider);

  }

}
