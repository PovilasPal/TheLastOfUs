package lt.techin.DentistryService.dto.userProvider;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lt.techin.DentistryService.model.Role;

import java.util.List;

public record UserProviderRequestDTO(
        @NotNull
        @Pattern(regexp = "^[A-Z]{3}[0-9]{3}$", message = "Licence number must be of three uppercase letters and 5 digits")
        String licenceNumber,
        @NotNull
        String name,
        @NotNull
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$")
        String email,
        @NotNull
        @Pattern(regexp = "^+d{1,3}d{9}$")
        String phoneNumber,
        @NotNull
        String username,
        @NotNull
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*W)(?!.* ).{8,16}$", message = "Password must contain one digit from 1 to 9, one lowercase letter, one uppercase letter, one special character, no space, and it must be 8-16 characters long.")
        String password,
        @NotNull
        List<Role> roles
) {
}
