package com.thusith.booking.controller;

import com.thusith.booking.modal.User;
import com.thusith.booking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/api/users")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/api/users")
    public User getUser(){
        User user = new User();
        user.setEmail("anjulathusith@gmail.com");
        user.setFullName("Thusith Anjula");
        user.setPhone("+94778661293");
        user.setRole("Customer");
        return user;
    }
}
