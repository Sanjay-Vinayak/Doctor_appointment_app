package com.doctorapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "doctors")
public class Doctor {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String firstName;
        private String lastName;
        @Column(unique = true, nullable = false)
        private String email;
        private String phone;
        private String city;
        private String specialization;
        private String qualification;
        private String password;
        private boolean isAvailableToday;

        public boolean isAvailableToday() {
                return isAvailableToday;
        }

        public void setAvailableToday(boolean availableToday) {
                isAvailableToday = availableToday;
        }

        @JsonIgnore
        @ManyToMany
        @JoinTable(
                name = "doctor_disease",
                joinColumns = @JoinColumn(name = "doctor_id"),
                inverseJoinColumns = @JoinColumn(name = "disease_id")
        )
        private Set<Disease> diseases;

        public Doctor() {}

        public Doctor(String firstName, String lastName, String email, String phone, String city, String specialization,String qualification, boolean isAvailableToday) {
                this.firstName = firstName;
                this.lastName = lastName;
                this.email = email;
                this.phone = phone;
                this.city = city;
                this.specialization = specialization;
                this.qualification = qualification;
                this.isAvailableToday = isAvailableToday;

        }

        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }

        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }

        public String getCity() {return city; }
        public void setCity(String city) { this.city = city; }

        public String getSpecialization() { return specialization; }
        public void setSpecialization(String specialization) { this.specialization = specialization; }

        public String getQualification() { return qualification; }
        public void setQualification(String qualification) { this.qualification = qualification; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public Set<Disease> getDiseases() { return diseases; }
        public void setDiseases(Set<Disease> diseases) { this.diseases = diseases; }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id;}
}

