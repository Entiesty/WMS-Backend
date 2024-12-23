package com.example.wmsbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.wmsbackend.DTO.DailyInStockData;
import com.example.wmsbackend.DTO.DailyOutStockData;
import com.example.wmsbackend.entity.StockTransaction;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

public interface StockTransactionMapper extends BaseMapper<StockTransaction> {

    @Select("SELECT DATE(transaction_time) AS date, " +
            "SUM(IF(transaction_type = 'in', quantity, 0)) AS total_in " +  // 使用 'IN' 对应的大小写一致
            "FROM xmut_stock_transaction " +
            "WHERE transaction_time BETWEEN #{startDate} AND #{endDate} " +   // 直接使用 LocalDate 作为参数
            "GROUP BY DATE(transaction_time) " +
            "ORDER BY DATE(transaction_time)")
    List<DailyInStockData> getDailyInStockData(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Select("SELECT DATE(transaction_time) AS date, " +
            "SUM(IF(transaction_type = 'out', quantity, 0)) AS total_out " +  // 使用 'OUT' 对应的大小写一致
            "FROM xmut_stock_transaction " +
            "WHERE transaction_time BETWEEN #{startDate} AND #{endDate} " +   // 直接使用 LocalDate 作为参数
            "GROUP BY DATE(transaction_time) " +
            "ORDER BY DATE(transaction_time)")
    List<DailyOutStockData> getDailyOutStockData(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);



}