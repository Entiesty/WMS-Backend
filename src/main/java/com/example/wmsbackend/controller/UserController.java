package com.example.wmsbackend.controller;

import com.example.wmsbackend.entity.QueryPageParam;
import com.example.wmsbackend.entity.ResponsePage;
import com.example.wmsbackend.entity.User;
import com.example.wmsbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/list")
    public ResponsePage<User> getUserByPage(@RequestBody QueryPageParam queryPageParam) {
        System.out.println("总记录数：" + userService.getUserPageData(queryPageParam).getTotal());
        return userService.getUserPageData(queryPageParam);
    }
}
