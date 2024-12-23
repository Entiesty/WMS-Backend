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
@TableName("xmut_stock_transaction")
public class StockTransaction {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("item_id")
    private Long itemId;
    private Long quantity;
    @TableField("transaction_type")
    private String transactionType;
    @TableField("source_warehouse_id")
    private Long sourceWarehouseId;
    @TableField("target_warehouse_id")
    private Long targetWarehouseId;
    @TableField("operator_id")
    private Long operatorId;
    @TableField("transaction_time")
    private Timestamp transactionTime;
}

