package lt.techin.DentistryService.dto.userProvider;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lt.techin.DentistryService.model.Role;

import java.util.List;

public record UserProviderRequestDTO(
        @NotNull
        @Pattern(regexp = "^[A-Z]{3}[0-9]{5}$", message = "Licence number must be in format ABC12345 (3 uppercase letters + 5 digits)")
        String licenceNumber,
        @NotNull
        String name,
        @NotNull
        @Email
        String email,
        @NotNull
        @Pattern(regexp = "^\\+\\d{1,3}\\d{9}$", message = "Phone number must start with a country code (+ followed by 1-3 digits) and have 9 additional digits (e.g., +37012345678)")
        String phoneNumber,
        @NotNull
        String username,
        @NotNull
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,16}$", message = "Password must contain one digit from 1 to 9, one lowercase letter, one uppercase letter, one special character (!@#$%^&*), no space, and it must be 8-16 characters long.")
        String password,
        @NotNull
        List<Role> roles
) {
}
