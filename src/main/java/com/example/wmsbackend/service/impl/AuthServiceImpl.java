package com.example.wmsbackend.service.impl;

import com.example.wmsbackend.entity.User;
import com.example.wmsbackend.service.AuthService;
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
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final RedisService redisService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<ApiResponse> login(User userVo) {
        if (userService.getUserByUserName(userVo.getUserName()) != null) {
            if (userService.validatePassword(userVo)) {
                if (userService.isAccountEnabled(userVo)) {
                    redisService.storeToken(userVo.getUserName());
                    HttpHeaders headers = new HttpHeaders();
                    headers.set("Authorization", "Bearer " + redisService.getToken(userVo.getUserName()));
                    headers.add("Access-Control-Expose-Headers", "Authorization");
                    System.out.println("这是TOKEN");
                    System.out.println(redisService.getToken(userVo.getUserName()));
                    System.out.println(headers.get("Authorization"));

                    return ResponseEntity.status(HttpStatus.OK).headers(headers).body(new ApiResponse("登录成功！", true));
                }
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse("账号已被禁用！", false));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("密码错误！", false));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("用户名不存在！", false));
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
