package com.example.wmsbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Route {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String path;
    private String name;
    @TableField("parent_id")
    private Long parentId;
    private String role;
    @TableField(exist = false)
    private List<Route> children = new ArrayList<>();
}
