package lt.techin.DentistryService.service;

import jakarta.persistence.EntityNotFoundException;
import lt.techin.DentistryService.dto.treatment.TreatmentRequestDTO;
import lt.techin.DentistryService.model.Treatment;
import lt.techin.DentistryService.model.UserProvider;
import lt.techin.DentistryService.repository.TreatmentRepository;
import lt.techin.DentistryService.repository.UserProviderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreatmentService {

  private final TreatmentRepository treatmentRepository;
  private final UserProviderRepository userProviderRepository;

  public TreatmentService(TreatmentRepository treatmentRepository, UserProviderRepository userProviderRepository) {
    this.treatmentRepository = treatmentRepository;
    this.userProviderRepository = userProviderRepository;
  }

  public List<Treatment> getAllTreatments() {
    return treatmentRepository.findAll();
  }

  public Treatment saveTreatment(Treatment treatment) {
    return treatmentRepository.save(treatment);
  }

  public Treatment getTreatmentById(Long id) {
    return treatmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Treatment not found with id: " + id));
  }

  public void deleteTreatment(Long treatmentId) {
    Treatment treatment = treatmentRepository.findById(treatmentId)
            .orElseThrow(() -> new EntityNotFoundException("Treatment not found"));

    List<UserProvider> providers = userProviderRepository.findAllByTreatmentsContaining(treatment);
    for (UserProvider provider : providers) {
      provider.getTreatments().remove(treatment);
      userProviderRepository.save(provider);
    }

    treatmentRepository.delete(treatment);
  }

  public Treatment updateTreatment(Long id, TreatmentRequestDTO requestDTO) {
    Treatment treatment = getTreatmentById(id);
    treatment.setName(requestDTO.name());
    treatment.setDescription(requestDTO.description());
    return treatmentRepository.save(treatment);
  }
}



