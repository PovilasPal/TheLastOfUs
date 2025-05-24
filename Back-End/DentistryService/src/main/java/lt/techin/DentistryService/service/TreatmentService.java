package lt.techin.DentistryService.service;

import lt.techin.DentistryService.model.Treatment;
import lt.techin.DentistryService.repository.TreatmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreatmentService {

  private final TreatmentRepository treatmentRepository;

  public TreatmentService(TreatmentRepository treatmentRepository) {
    this.treatmentRepository = treatmentRepository;
  }

  public List<Treatment> getAllTreatments() {
    return treatmentRepository.findAll();
  }

  // Optional: add more methods like save, findById, etc.
}


