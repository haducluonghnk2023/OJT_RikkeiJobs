package com.data.db_rikkeijobs.controller;

import com.data.db_rikkeijobs.dto.request.LoginRequest;
import com.data.db_rikkeijobs.dto.request.RegisterRequest;
import com.data.db_rikkeijobs.dto.response.ResponseWrapper;
import com.data.db_rikkeijobs.dto.response.UserResponse;
import com.data.db_rikkeijobs.entity.User;
import com.data.db_rikkeijobs.exception.HttpConflict;
import com.data.db_rikkeijobs.exception.HttpUnauthorized;
import com.data.db_rikkeijobs.mapper.UserMapper;
import com.data.db_rikkeijobs.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        if (userService.existsByEmail(request.getEmail())) {
            throw new HttpConflict("Email already exists: " + request.getEmail());
        }

        if (userService.findByUserName(request.getUserName()).isPresent()) {
            throw new HttpConflict("Username already exists: " + request.getUserName());
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setUserName(request.getUserName());
        user.setPassword(request.getPassword());
        user.setFirstName(null);
        user.setFullName(null);
        user.setStatus("Active");
        user.setRole("user");
        user.setLock(false);
        user.setCreateAt(java.time.LocalDateTime.now());

        UserResponse createdUser = userMapper.toResponse(userService.save(user));

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseWrapper.builder()
                        .status(HttpStatus.CREATED)
                        .code(HttpStatus.CREATED.value())
                        .data(createdUser)
                        .message("User registered successfully")
                        .build()
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        Optional<User> userOptional = userService.findByEmail(request.getEmail());
        if (userOptional.isEmpty()) {
            throw new HttpUnauthorized("Invalid email or password");
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new HttpUnauthorized("Invalid email or password");
        }

        if (Boolean.TRUE.equals(user.getLock())) {
            throw new HttpUnauthorized("Account is locked");
        }

        if ("inActive".equals(user.getStatus())) {
            throw new HttpUnauthorized("Account is inactive");
        }

        UserResponse userResponse = userMapper.toResponse(user);

        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(userResponse)
                        .message("Login successful")
                        .build()
        );
    }
}

