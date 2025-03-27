package com.doctorapp.controller;

import com.doctorapp.dto.DoctorDTO;
import com.doctorapp.dto.LoginRequest;
import com.doctorapp.dto.PatientDTO;
import com.doctorapp.model.*;
import com.doctorapp.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorProfileRepository doctorProfileRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;


    @PostMapping("/doctor/signup")
    public ResponseEntity<String> doctorSignup(@RequestBody DoctorDTO doctorDTO) {
        Doctor doctor = new Doctor();
        doctor.setFirstName(doctorDTO.getFirstName());
        doctor.setLastName(doctorDTO.getLastName());
        doctor.setEmail(doctorDTO.getEmail());
        doctor.setPhone(doctorDTO.getPhone());
        doctor.setCity(doctorDTO.getCity());
        doctor.setSpecialization(doctorDTO.getSpecialization());
        doctor.setQualification(doctorDTO.getQualification());
        doctor.setPassword(doctorDTO.getPassword());

        doctor = doctorRepository.save(doctor);

        DoctorProfile doctorProfile = new DoctorProfile();
        doctorProfile.setDoctorId(doctor.getId()); // Linking MySQL doctor ID
        doctorProfile.setDescription(doctorDTO.getDescription());
        doctorProfile.setImageUrl(doctorDTO.getImageUrl());

        doctorProfileRepository.save(doctorProfile);

        return ResponseEntity.ok("Doctor registered successfully");
    }

    @PostMapping("/patient/signup")
    public ResponseEntity<String> patientSignup(@RequestBody PatientDTO patientDTO) {
        Patient patient = new Patient();
        patient.setFirstName(patientDTO.getFirstName());
        patient.setLastName(patientDTO.getLastName());
        patient.setEmail(patientDTO.getEmail());
        patient.setPhone(patientDTO.getPhone());
        patient.setAddress(patientDTO.getAddress());
        patient.setCity(patientDTO.getCity());
        patient.setGender(patientDTO.getGender());
        patient.setDateOfBirth(patientDTO.getDateOfBirth());

        patient.setPassword(patientDTO.getPassword());
        patientRepository.save(patient);
        return ResponseEntity.ok("Patient registered successfully");
    }

    @PostMapping("/doctor/login")
    public ResponseEntity<?> doctorLogin(@RequestBody LoginRequest loginRequest) {
        Optional<Doctor> optionalDoctor = doctorRepository.findByEmail(loginRequest.getEmail());

        if (optionalDoctor.isPresent() ) {
            Doctor doctor = optionalDoctor.get();
            if (loginRequest.getPassword().equals(doctor.getPassword()) ) {
                List<Appointment> appointments = appointmentRepository.findByDoctorId(doctor.getId());
                Optional<DoctorProfile> doctorProfile = doctorProfileRepository.findByDoctorId(doctor.getId());

                Map<String, Object> response = new HashMap<>();
                response.put("message", "Doctor Logged in successfully");
                response.put("doctorDetails", doctor);
                response.put("appointments", appointments);
                response.put("profile", doctorProfile.orElse(null)); // If profile exists

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(401).body("Incorrect password of Doctor");
            }
        }
        return ResponseEntity.status(401).body("User not found with this email");

    }
    @PostMapping("/patient/login")
    public ResponseEntity<?> patientLogin(@RequestBody LoginRequest loginRequest) {
        Optional<Patient> optionalPatient = patientRepository.findByEmail(loginRequest.getEmail());

        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            if (loginRequest.getPassword().equals(patient.getPassword())) {
                List<Doctor> availableDoctors = doctorRepository.findByIsAvailableTodayTrue();
                return ResponseEntity.ok(new LoginResponse("Patient Logged in successfully", availableDoctors));
            } else {
                return ResponseEntity.status(401).body("Incorrect password of Patient");
            }
        }
        return ResponseEntity.status(401).body("User not found with this email");
    }

    static class LoginResponse {
        private String message;
        private List<Doctor> availableDoctors;

        public LoginResponse(String message, List<Doctor> availableDoctors) {
            this.message = message;
            this.availableDoctors = availableDoctors;
        }

        public String getMessage() {
            return message;
        }

        public List<Doctor> getAvailableDoctors() {
            return availableDoctors;
        }
        }
}
