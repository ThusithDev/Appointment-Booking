package com.thusith.booking.repository;

import com.thusith.booking.modal.DoctorAvailability;
import com.thusith.booking.modal.DoctorAvailabilityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability, DoctorAvailabilityId> {

    /**
    @Query("SELECT da.id.timeSlot FROM DoctorAvailability da WHERE da.doctor.id = :doctorId AND da.isAvailable = true")
    List<String> findAvailableSlotsByDoctorId(@Param("doctorId") Long doctorId);
    **/

    // @Query("SELECT da FROM DoctorAvailability da WHERE da.id.doctorId = :doctorId AND da.id.timeSlot = :timeSlot")
    @Query("SELECT COUNT(da) > 0 FROM DoctorAvailability da WHERE da.id.doctorId = :doctorId AND da.id.timeSlot = :timeSlot")
    boolean existsByDoctorIdAndTimeSlot(@Param("doctorId") Long doctorId, @Param("timeSlot") String timeSlot);

    @Query("SELECT da.id.timeSlot FROM DoctorAvailability da WHERE da.id.doctorId = :doctorId")
    List<String> findTimeSlotsByDoctorId(@Param("doctorId") Long doctorId);

}

