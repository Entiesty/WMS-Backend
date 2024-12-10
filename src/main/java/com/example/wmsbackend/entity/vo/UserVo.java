package com.example.wmsbackend.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
    private Long id;
    private String userName;
    private String password;
    private String role;
    private String status; // "启用" 或 "禁用"
    private String createdAt;
    private String updatedAt;
}

