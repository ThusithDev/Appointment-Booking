package com.thusith.booking.controller;

import com.thusith.booking.modal.Doctor;
import com.thusith.booking.repository.AppointmentRepository;
import com.thusith.booking.repository.DoctorAvailabilityRepository;
import com.thusith.booking.repository.DoctorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
  Author -: Thusith Wickramasinghe @github/ThusithDev
*/

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "*")
public class DoctorController {
    private final DoctorRepository doctorRepository;
    private final DoctorAvailabilityRepository doctorAvailabilityRepository;
    private final AppointmentRepository appointmentRepository;

    public DoctorController(DoctorRepository doctorRepository, DoctorAvailabilityRepository doctorAvailabilityRepository, AppointmentRepository appointmentRepository) {
        this.doctorRepository = doctorRepository;
        this.doctorAvailabilityRepository = doctorAvailabilityRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable Long id, @RequestParam(required = false) String date) {
        try {
            // Fetch doctor details
            Doctor doctor = doctorRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));

            // If no date is provided, return only doctor details
            if (date == null) {
                return ResponseEntity.ok(doctor);
            }

            // Fetch available time slots for the selected date
            List<String> defaultSlots = doctorAvailabilityRepository.findTimeSlotsByDoctorId(id);
            List<String> bookedSlots = appointmentRepository.findTimeSlotsByDoctorIdAndAppointmentDate(id, LocalDate.parse(date));

            // Filter out booked slots from default slots
            List<String> availableSlots = defaultSlots.stream()
                    .filter(slot -> !bookedSlots.contains(slot))
                    .collect(Collectors.toList());

            // Prepare the response
            Map<String, Object> response = new HashMap<>();
            response.put("id", doctor.getId());
            response.put("name", doctor.getName());
            response.put("specialty", doctor.getSpecialty());
            response.put("availability", availableSlots.stream()
                    .map(slot -> Map.of("timeSlot", slot, "available", true))
                    .collect(Collectors.toList()));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}