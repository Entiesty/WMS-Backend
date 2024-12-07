package com.example.wmsbackend.controller;

import com.example.wmsbackend.entity.User;
import com.example.wmsbackend.service.AuthorizationService;
import com.example.wmsbackend.util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authorization")
@RequiredArgsConstructor
public class AuthorizationController {
    private final AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody User user, HttpServletRequest request) {
        return authorizationService.login(user, request);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody User user) {
        return authorizationService.register(user);
    }
}
