package com.data.db_rikkeijobs.service;

import com.data.db_rikkeijobs.dto.request.LoginRequest;
import com.data.db_rikkeijobs.dto.request.RegisterRequest;
import com.data.db_rikkeijobs.dto.response.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;

import java.util.List;

public interface AuthService {
    UserResponse register(RegisterRequest request);
    UserResponse login(LoginRequest request);
    void logout(HttpServletRequest request);
    List<ResponseCookie> buildLogoutCookies();
}


