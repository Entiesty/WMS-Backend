package com.example.wmsbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("xmut_item")
public class Item {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("item_name")
    private String itemName;
    private BigDecimal price;
    @TableField("image_url")
    private String imageUrl;
    private Long stock;
    @TableField("item_category_id")
    private Long itemCategoryId;
    @TableField("warehouse_id")
    private Long warehouseId;
}
