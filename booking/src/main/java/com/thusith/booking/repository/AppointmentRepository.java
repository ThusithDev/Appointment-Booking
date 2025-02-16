package com.thusith.booking.repository;

import com.thusith.booking.modal.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByDoctorIdAndAppointmentDateAndTimeSlot(Long doctorId, LocalDate appointmentDate, String timeSlot);

    @Query("SELECT a.timeSlot FROM Appointment a WHERE a.doctor.id = :doctorId AND a.appointmentDate = :appointmentDate")
    List<String> findTimeSlotsByDoctorIdAndAppointmentDate(@Param("doctorId") Long doctorId, @Param("appointmentDate") LocalDate appointmentDate);
    List<Appointment> findByDoctorIdAndAppointmentDate(Long doctorId, LocalDate appointmentDate);

    List<Appointment> findByUserId(Long userId);
}


