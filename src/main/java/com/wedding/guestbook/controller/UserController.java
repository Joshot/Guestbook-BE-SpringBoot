package com.wedding.guestbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.wedding.guestbook.dto.LoginRequest;
import com.wedding.guestbook.dto.RegisterRequest;
import com.wedding.guestbook.model.User;
import com.wedding.guestbook.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            userService.registerUser(request.getUsername(), request.getEmail(), request.getPassword());
            return ResponseEntity.ok("Registrasi berhasil! Silakan login.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            User user = userService.loginUser(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}