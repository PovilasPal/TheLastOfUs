package lt.techin.DentistryService.controller;

import jakarta.validation.Valid;
import lt.techin.DentistryService.dto.login.LoginRequestDTO;
import lt.techin.DentistryService.security.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;

  public AuthController(AuthenticationManager authenticationManager,
                        JwtUtils jwtUtils) {
    this.authenticationManager = authenticationManager;
    this.jwtUtils = jwtUtils;
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDTO request) {
    Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.username(),
                    request.password()
            )
    );

    String token = jwtUtils.generateToken(
            (UserDetails) auth.getPrincipal()
    );

    return ResponseEntity.ok(token);
  }
}



