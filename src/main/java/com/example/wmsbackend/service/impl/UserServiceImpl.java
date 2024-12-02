package com.example.wmsbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wmsbackend.entity.User;
import com.example.wmsbackend.mapper.UserMapper;
import com.example.wmsbackend.service.RedisService;
import com.example.wmsbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final RedisService redisService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public User getUserByUserName(String userName) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name", userName);

        return this.getOne(userQueryWrapper);
    }

    @Override
    public boolean validatePassword(User userVo) {
        User userPo = getUserByUserName(userVo.getUserName());

        return passwordEncoder.matches(userVo.getPassword(), userPo.getPassword());
    }

    @Override
    public boolean isAccountEnabled(User user) {
        return user.getStatus() == 0;
    }
}
