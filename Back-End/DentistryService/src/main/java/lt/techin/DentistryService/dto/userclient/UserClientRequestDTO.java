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
        @Pattern(regexp = "^\\+?[1-9]\\d{7,20}$", message = "Phone number must start with a non-zero digit, may begin with '+', and contain 8 to 21 digits total (no letters or symbols).")
        String phoneNumber,

        @NotNull
        @Size(min = 2, max = 50, message = "Name needs to be minumum 1 and maximum 50 symbols")
        String username,

        @NotNull
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$", message = "At least one digit, lower case letter, upper case letter. No whitespace allowed. Minimum 8 symbols.")
        String password,

        List<Role> roles
) {
}
