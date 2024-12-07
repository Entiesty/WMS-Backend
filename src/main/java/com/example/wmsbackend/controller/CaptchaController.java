package com.example.wmsbackend.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
public class CaptchaController {
    private final RedisTemplate<String, String> redisTemplate;

    @GetMapping("/authorization/captcha")
    public void getCaptcha(HttpServletResponse response, HttpServletRequest request) throws IOException {

        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(100, 30, 4, 100);
        String code = captcha.getCode();


        String captchaKey = "captcha:" + request.getSession().getId();
        System.out.println(captchaKey);
        redisTemplate.opsForValue().set(captchaKey, code, 60, TimeUnit.SECONDS);

        System.out.println("验证码已生成");
        response.setContentType("image/png");
        captcha.write(response.getOutputStream());
    }
}
