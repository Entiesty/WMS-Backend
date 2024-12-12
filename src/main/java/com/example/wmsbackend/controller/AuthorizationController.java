package com.example.wmsbackend.controller;

import com.example.wmsbackend.entity.User;
import com.example.wmsbackend.service.AuthorizationService;
import com.example.wmsbackend.util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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

    @GetMapping("/logout")
    public ResponseEntity<ApiResponse> logout(HttpServletRequest request) {
        return authorizationService.logout(request);
    }

    @GetMapping("/captcha")
    public ResponseEntity<ApiResponse> getCaptcha(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        return authorizationService.getCaptcha(response, request);
    }
}
