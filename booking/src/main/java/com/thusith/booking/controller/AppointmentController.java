package com.thusith.booking.controller;

import com.thusith.booking.modal.Appointment;
import com.thusith.booking.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/book")
    public ResponseEntity<?> bookAppointment(@RequestBody Map<String, Object> payload) {
        try {
            if (!payload.containsKey("doctorId") || !payload.containsKey("userId") ||
                    !payload.containsKey("date") || !payload.containsKey("timeSlot")) {
                return ResponseEntity.badRequest().body(Map.of("error", "Missing required fields"));
            }

            Long doctorId = parseLong(payload.get("doctorId"));
            Long userId = parseLong(payload.get("userId"));
            String date = (String) payload.get("date");
            String timeSlot = (String) payload.get("timeSlot");

            Appointment appointment = appointmentService.bookAppointment(doctorId, userId, date, timeSlot);
            return ResponseEntity.ok(Map.of("message", "Appointment booked successfully", "appointmentId", appointment.getId()));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAppointments(@RequestParam Long userId) {
        try {
            List<Appointment> appointments = appointmentService.getAppointmentsByUserId(userId);
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id) {
        try {
            appointmentService.deleteAppointment(id);
            return ResponseEntity.ok(Map.of("message", "Appointment deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/available")
    public ResponseEntity<?> getAvailableSlots(@RequestParam Long doctorId, @RequestParam String date) {
        List<String> availableSlots = appointmentService.getAvailableSlots(doctorId, date);
        return ResponseEntity.ok(Map.of("availableSlots", availableSlots));
    }


    // Helper method to safely parse Long values
    private Long parseLong(Object value) {
        try {
            return value != null ? Long.valueOf(value.toString()) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}