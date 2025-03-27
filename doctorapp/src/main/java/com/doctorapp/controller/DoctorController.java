package com.doctorapp.controller;

import com.doctorapp.model.Doctor;

import com.doctorapp.model.DoctorProfile;
import com.doctorapp.repository.DoctorProfileRepository;
import com.doctorapp.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/doctors")
@CrossOrigin(origins = "*")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorProfileRepository doctorProfileRepository;

    // Search by doctor name
    @GetMapping("/search/name/{name}")
    public ResponseEntity<List<Map<String, Object>>> searchByDoctorName(@PathVariable String name) {
        List<Doctor> doctors = doctorRepository.findByFirstNameContainingIgnoreCase(name);
        return ResponseEntity.ok(mapDoctorsWithProfiles(doctors));
    }

    // Search by specialization (linked to disease)
    @GetMapping("/search/disease/{disease}")
    public ResponseEntity<List<Map<String, Object>>> searchByDisease(@PathVariable String disease) {
        List<Doctor> doctors = doctorRepository.findByDiseaseName(disease);
        return ResponseEntity.ok(mapDoctorsWithProfiles(doctors));
    }

    // Get all available doctors today
    @GetMapping("/available-today")
    public ResponseEntity<List<Map<String, Object>>> getAvailableDoctorsToday() {
        List<Doctor> doctors = doctorRepository.findByIsAvailableTodayTrue();
        return ResponseEntity.ok(mapDoctorsWithProfiles(doctors));
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<Map<String, Object>> getDoctorDetails(@PathVariable Long doctorId) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
        Optional<DoctorProfile> profileOptional = doctorProfileRepository.findByDoctorId(doctorId);

        if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            Map<String, Object> doctorDetails = mapDoctorWithProfile(doctor, profileOptional);
            return ResponseEntity.ok(doctorDetails);
        } else {
            return ResponseEntity.status(404).body(Collections.singletonMap("message", "Doctor not found"));
        }
    }

    private List<Map<String, Object>> mapDoctorsWithProfiles(List<Doctor> doctors) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (Doctor doctor : doctors) {
            Optional<DoctorProfile> profileOptional = doctorProfileRepository.findByDoctorId(doctor.getId());
            resultList.add(mapDoctorWithProfile(doctor, profileOptional));
        }
        return resultList;
    }

    private Map<String, Object> mapDoctorWithProfile(Doctor doctor, Optional<DoctorProfile> profileOptional) {
        Map<String, Object> doctorData = new HashMap<>();
        doctorData.put("id", doctor.getId());
        doctorData.put("firstName", doctor.getFirstName());
        doctorData.put("lastName", doctor.getLastName());
        doctorData.put("email", doctor.getEmail());
        doctorData.put("phone", doctor.getPhone());
        doctorData.put("city", doctor.getCity());
        doctorData.put("specialization", doctor.getSpecialization());
        doctorData.put("qualification", doctor.getQualification());
        doctorData.put("isAvailableToday", doctor.isAvailableToday());


        if (profileOptional.isPresent()) {
            DoctorProfile profile = profileOptional.get();
            doctorData.put("description", profile.getDescription());
            doctorData.put("imageUrl", profile.getImageUrl());
        } else {
            doctorData.put("description", "No description available");
            doctorData.put("imageUrl", null);
        }
        return doctorData;
    }

    @PutMapping("/update-availability/{doctorId}")
    public ResponseEntity<Map<String, String>> updateDoctorAvailability(@PathVariable Long doctorId, @RequestParam boolean isAvailable) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(doctorId);

        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            doctor.setAvailableToday(isAvailable);
            doctorRepository.save(doctor);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Doctor availability updated successfully");
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(404).body(Collections.singletonMap("message", "Doctor not found"));
    }
    @PutMapping("/{doctorId}/update")
    public ResponseEntity<String> updateDoctorProfile(@PathVariable Long doctorId, @RequestBody Doctor updatedDoctor) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);

        if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            doctor.setFirstName(updatedDoctor.getFirstName());
            doctor.setLastName(updatedDoctor.getLastName());
            doctor.setEmail(updatedDoctor.getEmail());
            doctor.setPhone(updatedDoctor.getPhone());
            doctor.setCity(updatedDoctor.getCity());
            doctor.setSpecialization(updatedDoctor.getSpecialization());
            doctor.setQualification(updatedDoctor.getQualification());
            doctor.setAvailableToday(updatedDoctor.isAvailableToday());

            doctorRepository.save(doctor);
            return ResponseEntity.ok("Doctor profile updated successfully");
        }
        return ResponseEntity.status(404).body("Doctor not found");
    }
    @DeleteMapping("/{doctorId}/delete")
    public ResponseEntity<String> deleteDoctorProfile(@PathVariable Long doctorId) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);

        if (doctorOptional.isPresent()) {
            doctorRepository.deleteById(doctorId);
            doctorProfileRepository.deleteByDoctorId(doctorId);
            return ResponseEntity.ok("Doctor profile deleted successfully");
        }
        return ResponseEntity.status(404).body("Doctor not found");
    }
}