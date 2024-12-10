package com.example.wmsbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wmsbackend.converter.UserConverterMapper;
import com.example.wmsbackend.entity.QueryPageParam;
import com.example.wmsbackend.entity.ResponsePage;
import com.example.wmsbackend.entity.User;
import com.example.wmsbackend.entity.vo.UserVo;
import com.example.wmsbackend.mapper.UserMapper;
import com.example.wmsbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public ResponsePage<UserVo> getUserPageData(QueryPageParam queryPageParam) {
        Page<User> page = new Page<>(queryPageParam.getCurrent(), queryPageParam.getSize());

        Page<User> queryPageUser = userMapper.selectPage(page, null);
        ResponsePage<UserVo> userPageData = new ResponsePage<>();

        List<User> records = queryPageUser.getRecords();
        List<UserVo> userVoList = new ArrayList<>();
        for (User user : records) {
            userVoList.add(UserConverterMapper.INSTANCE.toVO(user));
        }

        userPageData.setTotal(queryPageUser.getTotal());
        userPageData.setRecords(userVoList);

        return userPageData;
    }

    @Override
    public boolean removeUserById(Long id) {
        return this.removeById(id);
    }

    @Override
    public boolean updateUserById(UserVo userVo) {
        User user = UserConverterMapper.INSTANCE.toPO(userVo);
        if (!Objects.equals(userVo.getPassword(), this.getById(userVo.getId()).getPassword())) {
            String encryptedPassword = passwordEncoder.encode(userVo.getPassword());
            user.setPassword(encryptedPassword);
        }

        return this.updateById(user);
    }
}
