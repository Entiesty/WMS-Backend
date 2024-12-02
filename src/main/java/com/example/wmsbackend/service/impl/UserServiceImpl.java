package com.example.wmsbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wmsbackend.entity.User;
import com.example.wmsbackend.mapper.UserMapper;
import com.example.wmsbackend.service.RedisService;
import com.example.wmsbackend.service.UserService;
import com.example.wmsbackend.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final RedisService redisService;
    @Override
    public User getUserByUserName(String userName) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name", userName);

        return this.getOne(userQueryWrapper);
    }

    @Override
    public boolean validatePassword(User userVo) {
        User userPo = getUserByUserName(userVo.getUserName());

        return userPo.getPassword().equals(userVo.getPassword());
    }

    @Override
    public boolean isAccountEnabled(User user) {

        return user.getStatus() == 0;
    }

    @Override
    public ResponseEntity<ApiResponse> login(User userVo) {
        if(getUserByUserName(userVo.getUserName()) != null){
            if(validatePassword(userVo)){
                if(isAccountEnabled(userVo)){
                    redisService.storeToken(userVo.getUserName());
                    return ResponseEntity.ok(new ApiResponse("登录成功", true));
                }
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse("账号已被禁用", false));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("密码错误", false));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("用户名不存在", false));
    }
}
