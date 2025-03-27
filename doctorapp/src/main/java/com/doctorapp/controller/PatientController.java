package com.doctorapp.controller;

import com.doctorapp.model.Patient;
import com.doctorapp.repository.PatientRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientRepository patientRepository;

    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @GetMapping("/profile")
    public Patient getProfile(@RequestParam String email) {
        Optional<Patient> patient = patientRepository.findByEmail(email);
        return patient.orElseThrow(() -> new RuntimeException("Patient not found"));
    }

    @PutMapping("/update")
    public Patient updateProfile(@RequestParam String email, @RequestBody Patient updatedPatient) {
        Optional<Patient> patientOptional = patientRepository.findByEmail(email);
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();

            if (updatedPatient.getFirstName() != null) {
                patient.setFirstName(updatedPatient.getFirstName());
            }
            if (updatedPatient.getLastName() != null) {
                patient.setLastName(updatedPatient.getLastName());
            }
            if (updatedPatient.getPhone() != null) {
                patient.setPhone(updatedPatient.getPhone());
            }
            if (updatedPatient.getAddress() != null) {
                patient.setAddress(updatedPatient.getAddress());
            }
            if (updatedPatient.getCity() != null) {
                patient.setCity(updatedPatient.getCity());
            }
            if (updatedPatient.getGender() != null) {
                patient.setGender(updatedPatient.getGender());
            }
            if (updatedPatient.getDateOfBirth() != null) {
                patient.setDateOfBirth(updatedPatient.getDateOfBirth());
            }
            if (updatedPatient.getPassword() != null) {
                patient.setPassword(updatedPatient.getPassword());
            }

            return patientRepository.save(patient);
        } else {
            throw new RuntimeException("Patient not found!");
        }
    }
    @DeleteMapping("/delete")
    public String deleteProfile(@RequestParam String email) {
        Optional<Patient> patientOptional = patientRepository.findByEmail(email);
        if (patientOptional.isPresent()) {
            patientRepository.delete(patientOptional.get());
            return "Patient profile deleted successfully!";
        } else {
            throw new RuntimeException("Patient not found!");
        }
    }
}
