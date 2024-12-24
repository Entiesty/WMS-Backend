package com.example.wmsbackend.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockTransactionRankingVo {
    private String itemName;  // 商品名称
    private Long totalIn;     // 总进货数量
    private Long totalOut;    // 总出货数量
}

