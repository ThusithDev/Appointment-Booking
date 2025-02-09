package com.thusith.booking.repository;

import com.thusith.booking.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
