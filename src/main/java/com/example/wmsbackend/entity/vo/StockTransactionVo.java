package com.example.wmsbackend.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockTransactionVo {
    private Long id;
    private String itemName;
    private Long quantity;
    private String transactionType;
    private String sourceWarehouseName;
    private String targetWarehouseName;
    private String operatorName;
    private String transactionTime;
}
