package com.thusith.booking.controller;

import com.thusith.booking.modal.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

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
