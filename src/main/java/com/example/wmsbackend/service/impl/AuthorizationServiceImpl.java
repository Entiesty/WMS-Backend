package com.example.wmsbackend.service.impl;

import com.example.wmsbackend.entity.User;
import com.example.wmsbackend.service.AuthorizationService;
import com.example.wmsbackend.service.RedisService;
import com.example.wmsbackend.service.UserService;
import com.example.wmsbackend.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {
    private final UserService userService;
    private final RedisService redisService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<ApiResponse> login(User userVo) {
        User user = userService.getUserByUserName(userVo.getUserName());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("用户名不存在！", false));
        }
        if (!userService.validatePassword(user)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse("密码错误！", false));
        }
        if (!userService.isAccountEnabled(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse("账号已被禁用！", false));
        }

        redisService.storeToken(userVo.getUserName());
        String token = redisService.getToken(user.getUserName());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.add("Access-Control-Expose-Headers", "authorization");

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .body(new ApiResponse("登录成功！", true));
    }

    @Override
    public ResponseEntity<ApiResponse> register(User user) {
        if (userService.getUserByUserName(user.getUserName()) == null) {
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);
            userService.save(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("注册成功！", true));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("用户名已存在！", false));
    }
}
