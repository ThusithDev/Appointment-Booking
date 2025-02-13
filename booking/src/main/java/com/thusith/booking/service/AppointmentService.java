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
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final DoctorAvailabilityRepository doctorAvailabilityRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository,
                              UserRepository userRepository, DoctorAvailabilityRepository doctorAvailabilityRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
        this.doctorAvailabilityRepository = doctorAvailabilityRepository;
    }

    public Appointment bookAppointment(Long doctorId, Long userId, String date, String timeSlot) {
        Optional<Doctor> doctorOpt = doctorRepository.findById(doctorId);
        Optional<User> userOpt = userRepository.findById(userId);

        if (doctorOpt.isEmpty() || userOpt.isEmpty()) {
            throw new RuntimeException("Doctor or User not found");
        }

        Doctor doctor = doctorOpt.get();
        User user = userOpt.get();
        LocalDate appointmentDate = LocalDate.parse(date);

        // Check if time slot is available
        Optional<DoctorAvailability> availabilityOpt = doctorAvailabilityRepository.findById(new DoctorAvailabilityId(doctorId, timeSlot));
        if (availabilityOpt.isEmpty() || !availabilityOpt.get().isAvailable()) {
            throw new RuntimeException("Time slot is not available");
        }

        // Mark the slot as booked
        DoctorAvailability availability = availabilityOpt.get();
        availability.setAvailable(false);
        doctorAvailabilityRepository.save(availability);

        // Save the appointment
        Appointment appointment = new Appointment(doctor, user, appointmentDate, timeSlot);
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointmentsByUserId(Long userId) {
        return appointmentRepository.findByUserId(userId);
    }

    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    public List<String> getAvailableSlots(Long doctorId, String date) {
        List<String> allSlots = List.of("08:00 AM", "09:00 AM", "12:00 PM", "03:00 PM");

        // Fetch booked slots for the given date
        List<String> bookedSlots = appointmentRepository
                .findByDoctorIdAndAppointmentDate(doctorId, LocalDate.parse(date))
                .stream()
                .map(Appointment::getTimeSlot)
                .collect(Collectors.toList());

        // Fetch available slots from `doctor_availability`
        List<String> availableSlots = doctorAvailabilityRepository.findAvailableSlotsByDoctorId(doctorId)
                .stream()
                .filter(slot -> !bookedSlots.contains(slot))
                .collect(Collectors.toList());

        return availableSlots;
    }
}