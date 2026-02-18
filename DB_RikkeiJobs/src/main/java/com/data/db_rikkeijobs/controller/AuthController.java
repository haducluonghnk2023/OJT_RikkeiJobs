package com.data.db_rikkeijobs.controller;

import com.data.db_rikkeijobs.dto.request.LoginRequest;
import com.data.db_rikkeijobs.dto.request.RegisterRequest;
import com.data.db_rikkeijobs.dto.response.ResponseWrapper;
import com.data.db_rikkeijobs.dto.response.UserResponse;
import com.data.db_rikkeijobs.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        UserResponse createdUser = authService.register(request);

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
        UserResponse userResponse = authService.login(request);

        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(userResponse)
                        .message("Login successful")
                        .build()
        );
    }

    /**
     * Logout endpoint to clear any server-side session and delete session/auth cookies.
     * Note: JS cannot reliably remove HttpOnly cookies; must be done by server Set-Cookie.
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        authService.logout(request);

        ResponseEntity.BodyBuilder builder = ResponseEntity.ok();
        for (ResponseCookie cleared : authService.buildLogoutCookies()) {
            builder.header("Set-Cookie", cleared.toString());
        }

        return builder.body(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(null)
                        .message("Logout successful")
                        .build()
        );
    }
}

