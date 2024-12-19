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
import com.example.wmsbackend.util.ApiResponse;
import com.example.wmsbackend.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

        // 使用 PaginationUtil 获取分页数据
        return PaginationUtil.getPaginatedData(page,
                (p) -> userMapper.selectPage(p, null),   // 查询方法，获取 User 数据
                UserConverterMapper.INSTANCE::toVO // 转换方法，将 User 转为 UserVo
        );
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

    @Override
    public ResponseEntity<ApiResponse> validateUserIsExisted(String newUserName, Long id) {
        String currentUserName = this.getById(id).getUserName();
        if (currentUserName.equals(newUserName)) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("用户名唯一！", true));
        } else if (this.getUserByUserName(newUserName) == null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("用户名唯一！", true));
        }
        else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("用户名已存在！", false));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> validateUserIsExisted(String userName) {
        if(this.getUserByUserName(userName) == null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("用户名唯一！", true));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("用户名已存在！", false));
        }
    }

    @Override
    public boolean addUser(UserVo userVo) {
        User user = UserConverterMapper.INSTANCE.toPO(userVo);
        String encryptedPassword = passwordEncoder.encode(userVo.getPassword());
        user.setPassword(encryptedPassword);

        return this.save(user);
    }
}
