package com.example.wmsbackend.controller;

import com.example.wmsbackend.entity.User;
import com.example.wmsbackend.service.AuthorizationService;
import com.example.wmsbackend.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthorizationController {
    private final AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody User user) {
        return authorizationService.login(user);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody User user) {
        return authorizationService.register(user);
    }
}
