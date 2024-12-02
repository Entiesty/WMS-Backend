package com.example.wmsbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.wmsbackend.entity.User;

public interface UserService extends IService<User> {
    User getUserByUserName(String userName);
    boolean validatePassword(User user);
    boolean isAccountEnabled(User user);
}
