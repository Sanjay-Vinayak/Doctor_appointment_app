package com.doctorapp.repository;

import com.doctorapp.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByEmail(String email);
    List<Doctor> findByFirstNameContainingIgnoreCase(String name);
    List<Doctor> findBySpecializationContainingIgnoreCase(String specialization);
    List<Doctor> findByIsAvailableTodayTrue();

    @Query("SELECT d FROM Doctor d JOIN d.diseases ds WHERE LOWER(ds.name) LIKE LOWER(CONCAT('%', :disease, '%')) AND d.isAvailableToday = true")
    List<Doctor> findByDiseaseName(String disease);
}

