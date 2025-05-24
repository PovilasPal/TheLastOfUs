package lt.techin.DentistryService.controller;

import jakarta.validation.Valid;
import lt.techin.DentistryService.dto.userclient.UserClientMapper;
import lt.techin.DentistryService.dto.userclient.UserClientRequestDTO;
import lt.techin.DentistryService.dto.userclient.UserClientResponseDTO;
import lt.techin.DentistryService.model.UserClient;
import lt.techin.DentistryService.service.UserClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5222/")
@Validated
@RestController
public class UserClientController {

  private final UserClientService userClientService;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserClientController(UserClientService userClientService, PasswordEncoder passwordEncoder) {
    this.userClientService = userClientService;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping("/users_clients")
  public ResponseEntity<List<UserClientResponseDTO>> getClientUsers() {

    List<UserClient> userClients = this.userClientService.findAllUsers();

    return ResponseEntity.ok(UserClientMapper.toClientListDTO(userClients));

  }

  @GetMapping("/users_clients/{id}")
  public ResponseEntity<UserClientResponseDTO> getUserClientById(@PathVariable long id) {

    Optional<UserClient> findUserClient = this.userClientService.findUserById(id);

    if (findUserClient.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(UserClientMapper.toClientDTO(findUserClient.get()));

  }

  @PostMapping("/users_clients")
  public ResponseEntity<Object> saveClientUser(@Valid @RequestBody UserClientRequestDTO userClientRequestDTO) {

    if (this.userClientService.existsUserClientByUsername(userClientRequestDTO.username())) {

      return ResponseEntity.badRequest().body("This username already exists");

    }

    UserClient userClient = UserClientMapper.toUserClient(userClientRequestDTO);

    userClient.setPassword(this.passwordEncoder.encode(userClientRequestDTO.password()));

    UserClient savedUserClient = this.userClientService.saveUserClient(userClient);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedUserClient.getId())
                            .toUri())
            .body(UserClientMapper.toClientDTO(savedUserClient));

  }
  
  @DeleteMapping("/users_clients/{id}")
  public ResponseEntity<Void> deleteUserClient(@PathVariable long id) {

    Optional<UserClient> findUserClient = this.userClientService.findUserById(id);

    if (findUserClient.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    this.userClientService.deleteUser(id);

    return ResponseEntity.noContent().build();
  }

  @PutMapping("/users_clients/{id}")
  public ResponseEntity<Object> updateClient(
          @PathVariable("id") long id,
          @Valid @RequestBody UserClientRequestDTO userClientRequestDTO) {

    Optional<UserClient> findUserClient = this.userClientService.findUserById(id);

    if (findUserClient.isPresent()) {
      UserClient updateUserClient = findUserClient.get();

      // Check if username exists and belongs to another user
      boolean usernameTaken = userClientService.existsUserClientByUsername(userClientRequestDTO.username());
      if (usernameTaken && !updateUserClient.getUsername().equals(userClientRequestDTO.username())) {
        return ResponseEntity.badRequest().body("This username already exists");
      }

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

    // If user not found, create new user (optional, depends on your logic)
    UserClient newUserClient = this.userClientService.saveUserClient(UserClientMapper.toUserClient(userClientRequestDTO));

    return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest()
                    .replacePath("/users_clients/{id}")
                    .buildAndExpand(newUserClient.getId())
                    .toUri()
    ).body(UserClientMapper.toClientDTO(newUserClient));
  }

}
