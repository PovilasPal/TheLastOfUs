package lt.techin.DentistryService.dto.treatment;

import lt.techin.DentistryService.model.Treatment;

import java.util.List;

public class TreatmentMapper {

  public static Treatment toTreatment(TreatmentRequestDTO treatmentRequestDTO) {
    return new Treatment(
            treatmentRequestDTO.name(),
            treatmentRequestDTO.description()
    );
  }

  public static TreatmentResponseDTO toTreatmentDTO(Treatment treatment) {
    return new TreatmentResponseDTO(
            treatment.getId(),
            treatment.getName(),
            treatment.getDescription()
    );
  }

  public static List<TreatmentResponseDTO> toTreatmentDTOList(List<Treatment> treatments) {
    return treatments.stream()
            .map(treatment -> new TreatmentResponseDTO(
                    treatment.getId(),
                    treatment.getName(),
                    treatment.getDescription()
            ))
            .toList();
  }
}

