package lt.techin.DentistryService.dto.appointment;

import lt.techin.DentistryService.model.Appointment;

public class AppointmentMapper {

  public static AppointmentResponseDTO toResponseDTO(Appointment appointment) {
    return new AppointmentResponseDTO(
            appointment.getId(),
            appointment.getDate(),
            appointment.getStartTime(),
            appointment.getEndTime()
    );
  }

  public static Appointment toEntity(AppointmentRequestDTO dto) {
    Appointment appointment = new Appointment();
    appointment.setDate(dto.date());
    appointment.setStartTime(dto.startTime());
    appointment.setEndTime(dto.endTime());
    return appointment;
  }
}

