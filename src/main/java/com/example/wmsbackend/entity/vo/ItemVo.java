package com.example.wmsbackend.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemVo {
    private Long id;
    private String itemName;
    private BigDecimal price;
    private String imageUrl;
    private Long stock;
    private String itemCategoryName;  // 用类别名称代替 itemCategoryId
    private String warehouseName;     // 用仓库名称代替 warehouseId
}

