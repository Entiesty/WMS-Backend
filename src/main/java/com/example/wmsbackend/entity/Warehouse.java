package com.example.wmsbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("xmut_warehouse")
public class Warehouse {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("warehouse_name")
    private String warehouseName;
    private String location;
    @TableField("created_at")
    private Timestamp createdAt;
    @TableField("updated_at")
    private Timestamp updatedAt;
    @TableField(exist = false)
    private List<String> roles;
}
