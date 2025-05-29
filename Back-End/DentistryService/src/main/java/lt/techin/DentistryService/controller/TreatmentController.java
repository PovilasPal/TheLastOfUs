package lt.techin.DentistryService.controller;

import jakarta.validation.Valid;
import lt.techin.DentistryService.dto.treatment.TreatmentMapper;
import lt.techin.DentistryService.dto.treatment.TreatmentRequestDTO;
import lt.techin.DentistryService.dto.treatment.TreatmentResponseDTO;
import lt.techin.DentistryService.model.Treatment;
import lt.techin.DentistryService.service.TreatmentService;
import lt.techin.DentistryService.service.UserProviderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TreatmentController {

  private final TreatmentService treatmentService;
  private final UserProviderService userProviderService;

  public TreatmentController(TreatmentService treatmentService, UserProviderService userProviderService) {
    this.treatmentService = treatmentService;
    this.userProviderService = userProviderService;
  }

  @PostMapping("/treatments")
  public ResponseEntity<TreatmentResponseDTO> createTreatment(@RequestBody TreatmentRequestDTO requestDTO) {
    Treatment treatment = TreatmentMapper.toTreatment(requestDTO);
    Treatment saved = treatmentService.saveTreatment(treatment);
    return ResponseEntity.ok(TreatmentMapper.toTreatmentDTO(saved));
  }

  @GetMapping("/treatments")
  public ResponseEntity<List<TreatmentResponseDTO>> getAllTreatments() {
    List<Treatment> treatments = treatmentService.getAllTreatments();
    return ResponseEntity.ok(TreatmentMapper.toTreatmentDTOList(treatments));
  }

  @GetMapping("/treatments/{id}")
  public ResponseEntity<TreatmentResponseDTO> getTreatmentById(@PathVariable Long id) {
    Treatment treatment = treatmentService.getTreatmentById(id);
    return ResponseEntity.ok(TreatmentMapper.toTreatmentDTO(treatment));
  }

  @DeleteMapping("/treatments/{id}")
  public ResponseEntity<Void> deleteTreatment(@PathVariable Long id) {
    treatmentService.deleteTreatment(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/treatments/{id}")
  public ResponseEntity<TreatmentResponseDTO> updateTreatment(
          @PathVariable Long id,
          @Valid @RequestBody TreatmentRequestDTO requestDTO
  ) {
    Treatment updated = treatmentService.updateTreatment(id, requestDTO);
    return ResponseEntity.ok(TreatmentMapper.toTreatmentDTO(updated));
  }

}
