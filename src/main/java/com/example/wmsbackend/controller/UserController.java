package com.example.wmsbackend.controller;

import com.example.wmsbackend.entity.QueryPageParam;
import com.example.wmsbackend.entity.ResponsePage;
import com.example.wmsbackend.entity.vo.UserVo;
import com.example.wmsbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/list")
    public ResponsePage<UserVo> getUserByPage(@RequestBody QueryPageParam queryPageParam) {
        return userService.getUserPageData(queryPageParam);
    }
}
