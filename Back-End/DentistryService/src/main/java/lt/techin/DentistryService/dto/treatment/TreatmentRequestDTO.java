package lt.techin.DentistryService.dto.treatment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TreatmentRequestDTO(

        @NotNull
        @Size(min = 1, max = 255, message = "Name needs to be minumum 1 and maximum 255 symbols")
        String name,

        String description
) {
}
