package lt.techin.DentistryService.dto.appointment;

import lt.techin.DentistryService.model.Appointment;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class AppointmentMapper {

  private AppointmentMapper() {
    // Prevent instantiation
  }

  public static AppointmentResponseDTO toResponseDTO(Appointment appointment) {
    if (appointment == null) return null;
    return new AppointmentResponseDTO(
            appointment.getId(),
            appointment.getDate(),
            appointment.getStartTime(),
            appointment.getEndTime()
    );
  }

  public static List<AppointmentResponseDTO> toResponseDTOList(List<Appointment> appointments) {
    if (appointments == null) return Collections.emptyList();
    return appointments.stream()
            .map(AppointmentMapper::toResponseDTO)
            .collect(Collectors.toList());
  }

  public static Appointment toEntity(AppointmentRequestDTO dto) {
    if (dto == null) return null;
    Appointment appointment = new Appointment();
    appointment.setDate(dto.date());
    appointment.setStartTime(dto.startTime());
    appointment.setEndTime(dto.endTime());
    return appointment;
  }

  public static List<Appointment> toEntityList(List<AppointmentRequestDTO> dtos) {
    if (dtos == null) return Collections.emptyList();
    return dtos.stream()
            .map(AppointmentMapper::toEntity)
            .collect(Collectors.toList());
  }
}


