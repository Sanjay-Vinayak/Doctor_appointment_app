package com.doctorapp.dto;

import java.time.LocalDateTime;

public class AppointmentDTO {

    private Long doctorId;
    private Long patientId;
    private LocalDateTime appointmentTime;


    public AppointmentDTO() {}

    public AppointmentDTO(Long doctorId, Long patientId, LocalDateTime appointmentTime) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.appointmentTime = appointmentTime;
    }

    // Getters and Setters
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public LocalDateTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalDateTime appointmentTime) { this.appointmentTime = appointmentTime; }
}
