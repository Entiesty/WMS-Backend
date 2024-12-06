package com.example.wmsbackend.service;

import com.example.wmsbackend.entity.User;
import com.example.wmsbackend.util.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface AuthorizationService {
    ResponseEntity<ApiResponse> login(User user);
    ResponseEntity<ApiResponse> register(User user);
}
