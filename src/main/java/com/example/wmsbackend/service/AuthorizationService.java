package com.example.wmsbackend.service;

import com.example.wmsbackend.entity.User;
import com.example.wmsbackend.util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface AuthorizationService {
    ResponseEntity<ApiResponse> login(User user, HttpServletRequest request, HttpServletResponse response);
    ResponseEntity<ApiResponse> register(User user);
    ResponseEntity<ApiResponse> logout(HttpServletRequest request);
    ResponseEntity<ApiResponse> getCaptcha(HttpServletResponse response, HttpServletRequest request) throws IOException;
}
