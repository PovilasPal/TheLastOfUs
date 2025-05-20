package lt.techin.DentistryService.repository;

import lt.techin.DentistryService.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
  List<Appointment> findByEmployeeLicenseNumber(String licenseNumber);
}

