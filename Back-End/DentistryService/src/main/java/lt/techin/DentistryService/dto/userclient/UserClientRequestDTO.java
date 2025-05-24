package lt.techin.DentistryService.dto.userclient;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lt.techin.DentistryService.model.Role;

import java.util.List;

public record UserClientRequestDTO(

        @NotNull
        @Size(min = 1, max = 30, message = "Name needs to be minumum 1 and maximum 30 symbols")
        String name,

        @NotNull
        @Size(min = 1, max = 40, message = "Name needs to be minumum 1 and maximum 40 symbols")
        String surname,

        @NotNull
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email must include a valid username, '@' symbol, and a domain name (e.g., user@example.com)."
        )
        String email,

        @NotNull
        @Size(min = 1, max = 20, message = "Name needs to be minumum 1 and maximum 20 symbols")
        @Pattern(regexp = "^\\+370\\d{8}$", message = "Phone number must be in the format +370 followed by 8 digits")
        String phoneNumber,

        @NotNull
        @Size(min = 2, max = 50, message = "Name needs to be minumum 1 and maximum 50 symbols")
        String username,

        @NotNull
        @Pattern(
                regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,16}$",
                message = "Password must contain one digit, one lowercase, one uppercase, one special character (!@#$%^&*), and be 8–16 characters long"
        )
        String password,

        List<Role> roles
) {
}
