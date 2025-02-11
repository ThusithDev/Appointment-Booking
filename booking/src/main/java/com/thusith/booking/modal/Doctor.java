package com.thusith.booking.modal;

import jakarta.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String specialty;

    @ElementCollection
    @CollectionTable(name = "doctor_availability", joinColumns = @JoinColumn(name = "doctor_id"))
    @MapKeyColumn(name = "time_slot")
    @Column(name = "is_available")
    private Map<String, Boolean> availability = new HashMap<>();

    public Doctor() {
        // Default time slots with availability set to true
        availability.put("08:00 AM", true);
        availability.put("09:00 AM", true);
        availability.put("12:00 PM", true);
        availability.put("03:00 PM", true);
    }

    public Doctor(String name, String specialty) {
        this.name = name;
        this.specialty = specialty;
        availability.put("08:00 AM", true);
        availability.put("09:00 AM", true);
        availability.put("12:00 PM", true);
        availability.put("03:00 PM", true);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public Map<String, Boolean> getAvailability() {
        return availability;
    }

    public void bookTimeSlot(String timeSlot) {
        if (availability.containsKey(timeSlot) && availability.get(timeSlot)) {
            availability.put(timeSlot, false);
        } else {
            throw new RuntimeException("Time slot is not available");
        }
    }
}
