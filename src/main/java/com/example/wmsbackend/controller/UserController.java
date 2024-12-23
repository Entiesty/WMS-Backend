package com.example.wmsbackend.controller;

import com.example.wmsbackend.converter.UserConverterMapper;
import com.example.wmsbackend.entity.QueryPageParam;
import com.example.wmsbackend.entity.ResponsePage;
import com.example.wmsbackend.entity.User;
import com.example.wmsbackend.entity.vo.UserVo;
import com.example.wmsbackend.service.UserService;
import com.example.wmsbackend.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getUserList() {
        return userService.list();
    }

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

    @GetMapping("/validate-username/update")
    public ResponseEntity<ApiResponse> validateUsername(@RequestParam String userName, @RequestParam Long userId) {
        System.out.println("Received username: " + userName + ", userId: " + userId);
        return userService.validateUserIsExisted(userName, userId);
    }

    @GetMapping("/validate-username/add")
    public ResponseEntity<ApiResponse> validateUsername(@RequestParam String userName) {
        System.out.println("Received username: " + userName);
        return userService.validateUserIsExisted(userName);
    }

    @PostMapping
    public boolean addUser(@RequestBody UserVo userVo) {
        return userService.addUser(userVo);
    }
}
