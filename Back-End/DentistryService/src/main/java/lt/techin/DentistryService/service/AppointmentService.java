package lt.techin.DentistryService.service;

import lt.techin.DentistryService.model.Appointment;
import lt.techin.DentistryService.model.Employee;
import lt.techin.DentistryService.repository.AppointmentRepository;
import lt.techin.DentistryService.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

  private final AppointmentRepository appointmentRepository;
  private final EmployeeRepository employeeRepository;

  public AppointmentService(AppointmentRepository appointmentRepository, EmployeeRepository employeeRepository) {
    this.appointmentRepository = appointmentRepository;
    this.employeeRepository = employeeRepository;
  }

  public List<Appointment> getAppointmentsByEmployee(String employeeLicenseNumber) {
    return appointmentRepository.findByEmployeeLicenseNumber(employeeLicenseNumber);
  }

  public Appointment addAppointment(String employeeLicenseNumber, Appointment appointment) {
    Employee employee = employeeRepository.findById(employeeLicenseNumber)
            .orElseThrow(() -> new RuntimeException("Employee not found"));
    appointment.setEmployee(employee);
    return appointmentRepository.save(appointment);
  }

  public Appointment updateAppointment(String employeeLicenseNumber, Long appointmentId, Appointment updatedAppointment) {
    Appointment appointment = appointmentRepository.findById(appointmentId)
            .orElseThrow(() -> new RuntimeException("Appointment not found"));

    if (!appointment.getEmployee().getLicenseNumber().equals(employeeLicenseNumber)) {
      throw new RuntimeException("Unauthorized");
    }

    appointment.setDate(updatedAppointment.getDate());
    appointment.setStartTime(updatedAppointment.getStartTime());
    appointment.setEndTime(updatedAppointment.getEndTime());

    return appointmentRepository.save(appointment);
  }

  public void deleteAppointment(String employeeLicenseNumber, Long appointmentId) {
    Appointment appointment = appointmentRepository.findById(appointmentId)
            .orElseThrow(() -> new RuntimeException("Appointment not found"));

    if (!appointment.getEmployee().getLicenseNumber().equals(employeeLicenseNumber)) {
      throw new RuntimeException("Unauthorized");
    }

    appointmentRepository.delete(appointment);
  }
}

