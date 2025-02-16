package com.thusith.booking.modal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/*
  Author -: Thusith Wickramasinghe @github/ThusithDev
*/

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

    private LocalDate appointmentDate;
    private String timeSlot;
    private LocalDateTime bookingTime;

    public Appointment() {}

    public Appointment(Doctor doctor, User user, LocalDate appointmentDate, String timeSlot) {
        this.doctor = doctor;
        this.user = user;
        this.appointmentDate = appointmentDate;
        this.timeSlot = timeSlot;
        this.bookingTime = LocalDateTime.now();
    }

    // Setters (if needed)
    public void setId(Long id) {
        this.id = id;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }
}