package com.thusith.booking.modal;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class DoctorAvailabilityId implements Serializable {
    private Long doctorId;
    private String timeSlot;

    public DoctorAvailabilityId() {}

    public DoctorAvailabilityId(Long doctorId, String timeSlot) {
        this.doctorId = doctorId;
        this.timeSlot = timeSlot;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoctorAvailabilityId that = (DoctorAvailabilityId) o;
        return Objects.equals(doctorId, that.doctorId) &&
                Objects.equals(timeSlot, that.timeSlot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctorId, timeSlot);
    }
}

