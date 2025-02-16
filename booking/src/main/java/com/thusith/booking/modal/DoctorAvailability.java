package com.thusith.booking.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

/*
  Author -: Thusith Wickramasinghe @github/ThusithDev
*/

@Entity
public class DoctorAvailability {
    @EmbeddedId
    private DoctorAvailabilityId id;

    @ManyToOne
    @MapsId("doctorId")
    @JoinColumn(name = "doctor_id")
    @JsonIgnore  // Prevent infinite recursion
    private Doctor doctor;

    public DoctorAvailability() {}

    public DoctorAvailability(Doctor doctor, String timeSlot) {
        this.doctor = doctor;
        this.id = new DoctorAvailabilityId(doctor.getId(), timeSlot);
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public String getTimeSlot() {
        return id.getTimeSlot();
    }
}