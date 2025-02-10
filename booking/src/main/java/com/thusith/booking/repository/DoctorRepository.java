package com.thusith.booking.repository;

import com.thusith.booking.modal.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}