package lt.techin.DentistryService.controller;

import jakarta.validation.Valid;
import lt.techin.DentistryService.dto.appointment.AppointmentMapper;
import lt.techin.DentistryService.dto.appointment.AppointmentRequestDTO;
import lt.techin.DentistryService.dto.appointment.AppointmentResponseDTO;
import lt.techin.DentistryService.model.Appointment;
import lt.techin.DentistryService.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/provider/employees/{employeeLicenseNumber}/appointments")
public class AppointmentController {

  private final AppointmentService appointmentService;

  public AppointmentController(AppointmentService appointmentService) {
    this.appointmentService = appointmentService;
  }

  // Get all appointments for an employee
  @GetMapping
  public ResponseEntity<List<AppointmentResponseDTO>> getAppointments(
          @PathVariable String employeeLicenseNumber) {

    List<Appointment> appointments = appointmentService.getAppointmentsByEmployee(employeeLicenseNumber);

    List<AppointmentResponseDTO> dtos = appointments.stream()
            .map(AppointmentMapper::toResponseDTO)
            .collect(Collectors.toList());

    return ResponseEntity.ok(dtos);
  }

  // Add new appointment to an employee
  @PostMapping
  public ResponseEntity<AppointmentResponseDTO> addAppointment(
          @PathVariable String employeeLicenseNumber,
          @Valid @RequestBody AppointmentRequestDTO appointmentRequestDTO) {

    Appointment appointment = AppointmentMapper.toEntity(appointmentRequestDTO);
    Appointment savedAppointment = appointmentService.addAppointment(employeeLicenseNumber, appointment);

    return ResponseEntity.status(HttpStatus.CREATED)
            .body(AppointmentMapper.toResponseDTO(savedAppointment));
  }

  // Update appointment for an employee
  @PutMapping("/{appointmentId}")
  public ResponseEntity<AppointmentResponseDTO> updateAppointment(
          @PathVariable String employeeLicenseNumber,
          @PathVariable Long appointmentId,
          @Valid @RequestBody AppointmentRequestDTO appointmentRequestDTO) {

    Appointment updatedEntity = AppointmentMapper.toEntity(appointmentRequestDTO);
    Appointment updatedAppointment = appointmentService.updateAppointment(employeeLicenseNumber, appointmentId, updatedEntity);

    return ResponseEntity.ok(AppointmentMapper.toResponseDTO(updatedAppointment));
  }

  // Delete appointment for an employee
  @DeleteMapping("/{appointmentId}")
  public ResponseEntity<Void> deleteAppointment(
          @PathVariable String employeeLicenseNumber,
          @PathVariable Long appointmentId) {

    appointmentService.deleteAppointment(employeeLicenseNumber, appointmentId);
    return ResponseEntity.noContent().build();
  }
}

