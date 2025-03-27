package com.doctorapp.repository;

import com.doctorapp.model.DoctorProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorProfileRepository extends MongoRepository<DoctorProfile, String> {
    Optional<DoctorProfile> findByDoctorId(Long doctorId);

    void deleteByDoctorId(Long doctorId);
}

