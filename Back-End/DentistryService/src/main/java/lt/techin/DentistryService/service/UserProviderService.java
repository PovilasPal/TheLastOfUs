package lt.techin.DentistryService.service;

import lt.techin.DentistryService.model.Treatment;
import lt.techin.DentistryService.model.UserProvider;
import lt.techin.DentistryService.repository.TreatmentRepository;
import lt.techin.DentistryService.repository.UserProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserProviderService {

  private final UserProviderRepository userProviderRepository;
  private final TreatmentRepository treatmentRepository;

  @Autowired
  public UserProviderService(UserProviderRepository userProviderRepository, TreatmentRepository treatmentRepository) {
    this.userProviderRepository = userProviderRepository;
    this.treatmentRepository = treatmentRepository;
  }

  public Optional<UserProvider> findByUsername(String username) {
    return userProviderRepository.findByUsername(username);
  }

  public UserProvider saveUserProvider(UserProvider userProvider) {
    return this.userProviderRepository.save(userProvider);
  }

  public boolean existsByLicenseNumber(String licenseNumber) {
    return this.userProviderRepository.existsByLicenseNumber(licenseNumber);
  }

  public Optional<UserProvider> findByLicenseNumber(String licenseNumber) {
    return userProviderRepository.findById(licenseNumber);
  }

  public List<UserProvider> findAllProviders() {
    return userProviderRepository.findAll();
  }

  public void deleteUserProvider(String licenseNumber) {
    this.userProviderRepository.deleteById(licenseNumber);
  }

  public List<UserProvider> searchByAddress(String address) {
    return userProviderRepository.searchByAddress(address);
  }

  public void updateProviderTreatments(String licenseNumber, List<Long> treatmentIds) {
    UserProvider provider = userProviderRepository.findById(licenseNumber)
            .orElseThrow(() -> new RuntimeException("Provider not found with licenseNumber: " + licenseNumber));

    List<Treatment> treatments = treatmentRepository.findAllById(treatmentIds);

    provider.setTreatments(treatments);

    userProviderRepository.save(provider);
  }

}




