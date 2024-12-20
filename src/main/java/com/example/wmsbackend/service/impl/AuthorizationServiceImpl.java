package com.example.wmsbackend.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.example.wmsbackend.entity.User;
import com.example.wmsbackend.service.AuthorizationService;
import com.example.wmsbackend.service.RedisService;
import com.example.wmsbackend.service.UserService;
import com.example.wmsbackend.util.ApiResponse;
import com.example.wmsbackend.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {
    private final UserService userService;
    private final RedisService redisService;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtUtils jwtUtils;

    @Override
    public ResponseEntity<ApiResponse> login(User userVo, HttpServletRequest request, HttpServletResponse response) {
        String captchaKey = "captcha:" + request.getSession().getId();
        String correctCaptcha = redisTemplate.opsForValue().get(captchaKey);

        if (correctCaptcha != null && !correctCaptcha.equalsIgnoreCase(userVo.getCaptcha())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse("验证码错误或者已过期！", false));
        }
        redisTemplate.delete(captchaKey);

        User user = userService.getUserByUserName(userVo.getUserName());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("用户名不存在！", false));
        }
        if (!userService.validatePassword(userVo)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse("密码错误！", false));
        }
        if (!userService.isAccountEnabled(userVo)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse("账号已被禁用！", false));
        }

        redisService.storeToken(userVo.getUserName());
        String token = redisService.getToken(user.getUserName());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.add("Access-Control-Expose-Headers", "authorization");
        response.setHeader("role", user.getRole());
        response.setHeader("username", user.getUserName());

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

    @Override
    public ResponseEntity<ApiResponse> logout(HttpServletRequest request) {
        String header = request.getHeader("authorization");
        String token = header.substring(7).trim();
        String username = jwtUtils.getUserNameFromToken(token);

        redisService.deleteToken(username);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("登出成功！", true));
    }

    @Override
    public ResponseEntity<ApiResponse> getCaptcha(HttpServletResponse response, HttpServletRequest request)
            throws IOException {
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(100, 30, 4, 100);
        String code = captcha.getCode();


        String captchaKey = "captcha:" + request.getSession().getId();
        System.out.println(captchaKey);
        redisTemplate.opsForValue().set(captchaKey, code, 60, TimeUnit.SECONDS);

        System.out.println("验证码已生成");
        response.setContentType("image/png");
        captcha.write(response.getOutputStream());

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("验证码已生成！", true));
    }
}
