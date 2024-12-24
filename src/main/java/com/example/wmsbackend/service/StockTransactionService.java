package com.example.wmsbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.wmsbackend.entity.QueryPageParam;
import com.example.wmsbackend.entity.ResponsePage;
import com.example.wmsbackend.entity.StockTransaction;
import com.example.wmsbackend.entity.vo.StockTransactionRankingVo;
import com.example.wmsbackend.entity.vo.StockTransactionVo;
import com.example.wmsbackend.util.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface StockTransactionService extends IService<StockTransaction> {
    ResponseEntity<ApiResponse> addStockTransaction(StockTransactionVo stockTransactionVo);

    ResponsePage<StockTransactionVo> getStockTransactionPageData(QueryPageParam queryPageParam);
    List<StockTransactionRankingVo> getStockTransactionRanking(LocalDate startDate, LocalDate endDate);
}
