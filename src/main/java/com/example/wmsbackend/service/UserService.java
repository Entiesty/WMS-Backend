package com.example.wmsbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.wmsbackend.entity.QueryPageParam;
import com.example.wmsbackend.entity.ResponsePage;
import com.example.wmsbackend.entity.User;
import com.example.wmsbackend.entity.vo.UserVo;

public interface UserService extends IService<User> {
    User getUserByUserName(String userName);
    boolean validatePassword(User user);
    boolean isAccountEnabled(User user);
    ResponsePage<UserVo> getUserPageData(QueryPageParam queryPageParam);
    boolean removeUserById(Long id);
    boolean updateUserById(UserVo userVo);
}
