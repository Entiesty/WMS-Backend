package com.example.wmsbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("xumt_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("user_name")
    private String userName;
    private String password;
    private String role;
    private Integer status;
    @TableField("created_at")
    private Date createdAt;
    @TableField("update_at")
    private Date updatedAt;
}
