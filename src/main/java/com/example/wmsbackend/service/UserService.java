package com.example.wmsbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.wmsbackend.entity.User;
import com.example.wmsbackend.util.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface UserService extends IService<User> {
    User getUserByUserName(String userName);
    boolean validatePassword(User user);
    boolean isAccountEnabled(User user);

    ResponseEntity<ApiResponse> login(User user);
}
