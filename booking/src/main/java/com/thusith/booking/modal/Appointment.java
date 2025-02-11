package com.thusith.booking.modal;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String timeSlot;
    private LocalDateTime bookingTime;

    public Appointment() {}

    public Appointment(Doctor doctor, User user, String timeSlot) {
        this.doctor = doctor;
        this.user = user;
        this.timeSlot = timeSlot;
        this.bookingTime = LocalDateTime.now();
    }

}
