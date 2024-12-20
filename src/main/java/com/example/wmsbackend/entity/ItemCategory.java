package com.example.wmsbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("xmut_item_category")
public class ItemCategory {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("item_category_name")
    private String itemCategoryName;
    @TableField("item_category_description")
    private String itemCategoryDescription;
}
