package com.example.wmsbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("xmut_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("user_name")
    private String userName;
    private String password;
    private String role;
    private Integer status;
    @TableField("created_at")
    private Timestamp createdAt;
    @TableField("updated_at")
    private Timestamp updatedAt;
}
