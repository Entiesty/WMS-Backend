package com.example.wmsbackend.converter;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.wmsbackend.entity.Item;
import com.example.wmsbackend.entity.StockTransaction;
import com.example.wmsbackend.entity.vo.StockTransactionVo;
import com.example.wmsbackend.service.ItemService;
import com.example.wmsbackend.service.UserService;
import com.example.wmsbackend.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class StockTransactionConverter {

    private final ItemService itemService;
    private final WarehouseService warehouseService;
    private final UserService userService;

    // 从 PO 转换为 VO
    public StockTransactionVo toVO(StockTransaction stockTransaction) {
        StockTransactionVo stockTransactionVo = new StockTransactionVo();
        stockTransactionVo.setId(stockTransaction.getId());
        stockTransactionVo.setItemName(getItemNameById(stockTransaction.getItemId()));
        if (stockTransaction.getSourceWarehouseId() != null) {
            stockTransactionVo.setSourceWarehouseName(getSourceWarehouseNameById(stockTransaction.getSourceWarehouseId()));
        }
        if (stockTransaction.getTargetWarehouseId() != null) {
            stockTransactionVo.setTargetWarehouseName(getTargetWarehouseNameById(stockTransaction.getTargetWarehouseId()));
        }
        stockTransactionVo.setOperatorName(getOperatorNameById(stockTransaction.getOperatorId()));
        stockTransactionVo.setTransactionTime(timestampToDate(stockTransaction.getTransactionTime()));
        stockTransactionVo.setQuantity(stockTransaction.getQuantity());
        stockTransactionVo.setTransactionType(stockTransaction.getTransactionType());
        return stockTransactionVo;
    }

    // 从 VO 转换为 PO
    public StockTransaction toPO(StockTransactionVo stockTransactionVo) {
        StockTransaction stockTransaction = new StockTransaction();
        stockTransaction.setItemId(getItemByItemName(stockTransactionVo.getItemName()).getId());
        stockTransaction.setQuantity(stockTransactionVo.getQuantity());
        stockTransaction.setTransactionType(stockTransactionVo.getTransactionType());
        if (!stockTransactionVo.getSourceWarehouseName().isEmpty()) {
            stockTransaction.setSourceWarehouseId(getWarehouseIdByName(stockTransactionVo.getSourceWarehouseName()));
        }
        if (!stockTransactionVo.getTargetWarehouseName().isEmpty()) {
            stockTransaction.setTargetWarehouseId(getWarehouseIdByName(stockTransactionVo.getTargetWarehouseName()));
        }
        stockTransaction.setOperatorId(getUserIdByName(stockTransactionVo.getOperatorName()));
        stockTransaction.setTransactionTime(dateToTimestamp(stockTransactionVo.getTransactionTime()));
        return stockTransaction;
    }

    // 获取商品名称：通过 itemId 获取商品名称
    public String getItemNameById(Long itemId) {
        return itemService.getById(itemId).getItemName();  // 调用外部的 ItemService 获取商品名称
    }

    // 获取仓库名称：通过 warehouseId 获取仓库名称
    public String getSourceWarehouseNameById(Long sourceWarehouseId) {
        return warehouseService.getById(sourceWarehouseId).getWarehouseName();
    }

    // 获取目标仓库名称：通过 targetWarehouseId 获取目标仓库名称
    public String getTargetWarehouseNameById(Long targetWarehouseId) {
        return warehouseService.getById(targetWarehouseId).getWarehouseName();
    }

    // 获取操作人姓名：通过 operatorId 获取操作人名称
    public String getOperatorNameById(Long operatorId) {
        return userService.getById(operatorId).getUserName();
    }

    // 获取仓库 ID：通过仓库名称获取仓库 ID（假设是这样的需求）
    public Long getWarehouseIdByName(String warehouseName) {
        return warehouseService.getWarehouseByWarehouseName(warehouseName).getId();
    }

    // 获取操作人 ID：通过操作人名称获取操作人 ID（假设是这样的需求）
    public Long getUserIdByName(String userName) {
        return userService.getUserByUserName(userName).getId();
    }

    public Item getItemByItemName(String itemName) {
        LambdaQueryWrapper<Item> itemLambdaQueryWrapper = new LambdaQueryWrapper<>();
        itemLambdaQueryWrapper.eq(Item::getItemName, itemName);

        return itemService.getOne(itemLambdaQueryWrapper);
    }

    // 格式化时间戳为字符串
    public String timestampToDate(Timestamp timestamp) {
        return timestamp == null ? null : DateUtil.format(new Date(timestamp.getTime()), "yyyy-MM-dd HH:mm:ss");
    }

    // 格式化字符串为时间戳
    public Timestamp dateToTimestamp(String date) {
        return date == null ? null : Timestamp.valueOf(date);
    }
}

