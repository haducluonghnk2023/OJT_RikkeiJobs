package com.data.db_rikkeijobs.service.impl;

import com.data.db_rikkeijobs.dto.request.LoginRequest;
import com.data.db_rikkeijobs.dto.request.RegisterRequest;
import com.data.db_rikkeijobs.dto.response.UserResponse;
import com.data.db_rikkeijobs.entity.User;
import com.data.db_rikkeijobs.exception.HttpConflict;
import com.data.db_rikkeijobs.exception.HttpUnauthorized;
import com.data.db_rikkeijobs.mapper.UserMapper;
import com.data.db_rikkeijobs.service.AuthService;
import com.data.db_rikkeijobs.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse register(RegisterRequest request) {
        if (userService.existsByEmail(request.getEmail())) {
            throw new HttpConflict("Email already exists: " + request.getEmail());
        }
        if (userService.findByUserName(request.getUserName()).isPresent()) {
            throw new HttpConflict("Username already exists: " + request.getUserName());
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setUserName(request.getUserName());
        user.setPassword(request.getPassword()); // encoded in UserService.save
        user.setFirstName(null);
        user.setFullName(null);
        user.setStatus("Active");
        user.setRole("user");
        user.setLock(false);
        user.setCreateAt(LocalDateTime.now());

        return userMapper.toResponse(userService.save(user));
    }

    @Override
    public UserResponse login(LoginRequest request) {
        User user = userService.findByEmail(request.getEmail())
                .orElseThrow(() -> new HttpUnauthorized("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new HttpUnauthorized("Invalid email or password");
        }
        if (Boolean.TRUE.equals(user.getLock())) {
            throw new HttpUnauthorized("Account is locked");
        }
        if ("inActive".equals(user.getStatus())) {
            throw new HttpUnauthorized("Account is inactive");
        }

        return userMapper.toResponse(user);
    }

    @Override
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    @Override
    public List<ResponseCookie> buildLogoutCookies() {
        List<String> cookieNames = List.of(
                "JSESSIONID",
                "SESSION",
                "token",
                "accessToken",
                "refreshToken",
                "refresh_token"
        );

        return cookieNames.stream()
                .map(name -> ResponseCookie.from(name, "")
                        .path("/")
                        .httpOnly(true)
                        .maxAge(0)
                        .sameSite("Lax")
                        .build()
                )
                .toList();
    }
}


