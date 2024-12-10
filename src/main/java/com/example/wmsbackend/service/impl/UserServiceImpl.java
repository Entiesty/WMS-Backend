package com.example.wmsbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wmsbackend.entity.QueryPageParam;
import com.example.wmsbackend.entity.ResponsePage;
import com.example.wmsbackend.entity.User;
import com.example.wmsbackend.mapper.UserMapper;
import com.example.wmsbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public User getUserByUserName(String userName) {
        LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(User::getUserName, userName);

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

    @Override
    public ResponsePage<User> getUserPageData(QueryPageParam queryPageParam) {
        Page<User> page = new Page<>(queryPageParam.getCurrent(), queryPageParam.getSize());

        Page<User> queryPageUser = userMapper.selectPage(page, null);
        ResponsePage<User> userPageData = new ResponsePage<>();

        userPageData.setTotal(queryPageUser.getTotal());
        userPageData.setRecords(queryPageUser.getRecords());

        return userPageData;
    }
}
