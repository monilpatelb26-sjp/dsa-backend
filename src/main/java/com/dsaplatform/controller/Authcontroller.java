package com.dsaplatform.controller;

import com.dsaplatform.model.User;
import com.dsaplatform.repository.UserRepository;
import com.dsaplatform.security.Jwtutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class Authcontroller {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Jwtutil jwtUtil;

    // ── ASLI SIGNUP LOGIC ──
    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signup(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();

        // Check karna ki kya is email se pehle hi account hai?
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            response.put("message", "Email already exists! Please Sign In.");
            return ResponseEntity.badRequest().body(response);
        }

        // Naya user MySQL me save karna
        userRepository.save(user);

        response.put("message", "Account created successfully!");
        return ResponseEntity.ok(response);
    }

    // ── ASLI SIGNIN LOGIC ──
    @PostMapping("/signin")
    public ResponseEntity<Map<String, Object>> signin(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        String email = request.get("email");
        String password = request.get("password");

        // Database me email se user dhoondhna
        Optional<User> userOpt = userRepository.findByEmail(email);

        // Agar user mila AUR password match ho gaya
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {

            // Login success hone par JWT Token generate karna
            String token = jwtUtil.generateToken(email);

            response.put("token", token);
            response.put("name", userOpt.get().getName());
            return ResponseEntity.ok(response);

        } else {
            response.put("message", "Invalid email or password");
            return ResponseEntity.status(401).body(response);
        }
    }
}