package com.thusith.booking.service;

import com.thusith.booking.modal.*;
import com.thusith.booking.repository.AppointmentRepository;
import com.thusith.booking.repository.DoctorRepository;
import com.thusith.booking.repository.UserRepository;
import com.thusith.booking.repository.DoctorAvailabilityRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorAvailabilityRepository doctorAvailabilityRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, DoctorAvailabilityRepository doctorAvailabilityRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorAvailabilityRepository = doctorAvailabilityRepository;
    }

    public Appointment bookAppointment(Long doctorId, Long userId, String date, String timeSlot) {
        // Check if the time slot is already booked for the selected date
        boolean isSlotBooked = appointmentRepository.existsByDoctorIdAndAppointmentDateAndTimeSlot(doctorId, LocalDate.parse(date), timeSlot);
        if (isSlotBooked) {
            throw new RuntimeException("Time slot is already booked for the selected date");
        }

        // Check if the time slot is part of the doctor's default availability
        boolean isSlotAvailable = doctorAvailabilityRepository.existsByDoctorIdAndTimeSlot(doctorId, timeSlot);
        if (!isSlotAvailable) {
            throw new RuntimeException("Time slot is not available for the doctor");
        }

        // Create and save the appointment
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);

        User user = new User();
        user.setId(userId);

        Appointment appointment = new Appointment(doctor, user, LocalDate.parse(date), timeSlot);

        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointmentsByUserId(Long userId) {
        return appointmentRepository.findByUserId(userId);
    }

    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    public List<String> getAvailableSlots(Long doctorId, String date) {
        // Get the doctor's default available time slots
        List<String> defaultSlots = doctorAvailabilityRepository.findTimeSlotsByDoctorId(doctorId);

        // Get the time slots already booked for the selected date
        List<String> bookedSlots = appointmentRepository.findTimeSlotsByDoctorIdAndAppointmentDate(doctorId, LocalDate.parse(date));

        // Filter out booked slots from default slots
        return defaultSlots.stream()
                .filter(slot -> !bookedSlots.contains(slot))
                .collect(Collectors.toList());
    }
}