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

    private boolean isAvailable;

    public DoctorAvailability() {}

    public DoctorAvailability(Doctor doctor, String timeSlot, boolean isAvailable) {
        this.doctor = doctor;
        this.id = new DoctorAvailabilityId(doctor.getId(), timeSlot);
        this.isAvailable = isAvailable;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public String getTimeSlot() {
        return id.getTimeSlot();
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
