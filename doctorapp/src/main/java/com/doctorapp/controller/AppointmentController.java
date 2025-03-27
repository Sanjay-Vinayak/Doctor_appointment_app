package com.doctorapp.controller;

import com.doctorapp.dto.AppointmentDTO;
import com.doctorapp.model.*;
import com.doctorapp.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @PostMapping("/schedule")
    public ResponseEntity<String> scheduleAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        Optional<Doctor> doctorOpt = doctorRepository.findById(appointmentDTO.getDoctorId());
        Optional<Patient> patientOpt = patientRepository.findById(appointmentDTO.getPatientId());

        if (doctorOpt.isEmpty() || patientOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Doctor or Patient not found");
        }

        Appointment appointment = new Appointment(
                doctorOpt.get(),
                patientOpt.get(),
                appointmentDTO.getAppointmentTime(),
                "Scheduled"
        );

        appointmentRepository.save(appointment);
        return ResponseEntity.ok("Appointment scheduled successfully");
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<?> getAppointmentsByDoctor(@PathVariable Long doctorId) {
        return ResponseEntity.ok(appointmentRepository.findByDoctorId(doctorId));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<?> getAppointmentsByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(appointmentRepository.findByPatientId(patientId));
    }
}

