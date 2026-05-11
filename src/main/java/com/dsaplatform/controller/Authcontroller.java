package com.dsaplatform.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // 🚀 Ye line CORS error ko theek karti hai! Netlify ko allow karegi.
public class Authcontroller {

    // Login API
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginData) {
        // Yahan aapka database check karne ka logic aayega
        String email = loginData.get("email");
        String password = loginData.get("password");

        // Dummy response for testing
        Map<String, String> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("email", email);
        response.put("name", "Monil Patel");
        return ResponseEntity.ok(response);
    }

    // Register API
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> userData) {
        // Yahan aapka naya user MySQL mein save karne ka logic aayega (UserRepository.save)
        String name = userData.get("name");
        String email = userData.get("email");

        // Dummy response for testing
        Map<String, String> response = new HashMap<>();
        response.put("message", "Account created");
        response.put("email", email);
        response.put("name", name);
        return ResponseEntity.ok(response);
    }
}