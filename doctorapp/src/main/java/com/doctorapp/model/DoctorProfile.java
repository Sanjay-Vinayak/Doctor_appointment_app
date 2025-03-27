package com.doctorapp.model;

import org.springframework.data.annotation.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "doctor_profiles")
@Data

@AllArgsConstructor
public class DoctorProfile {
    @Id
    private String id;
    private Long doctorId;
    private String imageUrl;
    private String description;

    public DoctorProfile() {}

    public DoctorProfile(String doctorId, String longDescription) {
        this.doctorId = Long.valueOf(doctorId);
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}

