package com.example.wmsbackend.service;

import com.example.wmsbackend.entity.User;
import com.example.wmsbackend.util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface AuthorizationService {
    ResponseEntity<ApiResponse> login(User user, HttpServletRequest request);
    ResponseEntity<ApiResponse> register(User user);
    ResponseEntity<ApiResponse> logout(HttpServletRequest request);
}
