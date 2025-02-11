package com.thusith.booking.service;

import com.thusith.booking.modal.Appointment;
import com.thusith.booking.modal.Doctor;
import com.thusith.booking.modal.User;
import com.thusith.booking.repository.AppointmentRepository;
import com.thusith.booking.repository.DoctorRepository;
import com.thusith.booking.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository, UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
    }

    public Appointment bookAppointment(Long doctorId, Long userId, String timeSlot) {
        Optional<Doctor> doctorOpt = doctorRepository.findById(doctorId);
        Optional<User> userOpt = userRepository.findById(userId);

        if (doctorOpt.isEmpty() || userOpt.isEmpty()) {
            throw new RuntimeException("Doctor or User not found");
        }

        Doctor doctor = doctorOpt.get();
        User user = userOpt.get();

        // Check if the time slot is available
        if (!doctor.getAvailability().getOrDefault(timeSlot, false)) {
            throw new RuntimeException("Time slot is not available");
        }

        // Mark the slot as booked
        doctor.getAvailability().put(timeSlot, false);
        doctorRepository.save(doctor);

        // Save the appointment
        Appointment appointment = new Appointment(doctor, user, timeSlot);
        return appointmentRepository.save(appointment);
    }
}

