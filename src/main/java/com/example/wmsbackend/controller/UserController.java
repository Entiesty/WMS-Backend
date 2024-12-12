package com.example.wmsbackend.controller;

import com.example.wmsbackend.converter.UserConverterMapper;
import com.example.wmsbackend.entity.QueryPageParam;
import com.example.wmsbackend.entity.ResponsePage;
import com.example.wmsbackend.entity.vo.UserVo;
import com.example.wmsbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/list")
    public ResponsePage<UserVo> getUserByPage(@RequestBody QueryPageParam queryPageParam) {
        return userService.getUserPageData(queryPageParam);
    }

    @DeleteMapping("/{id}")
    public boolean deleteUserById(@PathVariable Long id) {
        return userService.removeUserById(id);
    }

    @PutMapping
    public boolean updateUserById(@RequestBody UserVo userVo) {
        return userService.updateUserById(userVo);
    }

    @GetMapping("/{userName}")
    public UserVo getUserByUserName(@PathVariable String userName) {
        return UserConverterMapper.INSTANCE.toVO(userService.getUserByUserName(userName));
    }
}
