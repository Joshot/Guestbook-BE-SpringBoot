package com.wedding.guestbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.wedding.guestbook.model.User;
import com.wedding.guestbook.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String username, String email, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username sudah terpakai!");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email sudah terpakai!");
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        
        // Enkripsi password sebelum masuk ke database Supabase
        newUser.setPassword(passwordEncoder.encode(password));

        return userRepository.save(newUser);
    }

    public User loginUser(String username, String rawPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Username tidak ditemukan!"));

        // Cocokkan password yang diketik dengan password acak di database
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Password salah!");
        }

        return user;
    }
}