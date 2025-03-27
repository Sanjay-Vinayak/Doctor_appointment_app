package com.doctorapp.dto;

import lombok.*;


@ToString
public class DoctorDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String city;
    private String specialization;
    private String qualification;
    private String password;
    private String description;
    private String imageUrl;

    public DoctorDTO() {}

    public DoctorDTO(String firstName, String lastName, String email, String phone,String city, String password, String specialization, String qualification, String description, String imageUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.password = password;
        this.specialization = specialization;
        this.qualification = qualification;
        this.description = description;
        this.imageUrl = imageUrl;
    }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
