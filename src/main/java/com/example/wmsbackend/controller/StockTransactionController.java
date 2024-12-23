package com.example.wmsbackend.controller;

import com.example.wmsbackend.converter.StockTransactionConverter;
import com.example.wmsbackend.entity.QueryPageParam;
import com.example.wmsbackend.entity.ResponsePage;
import com.example.wmsbackend.entity.vo.StockTransactionVo;
import com.example.wmsbackend.service.StockTransactionService;
import com.example.wmsbackend.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stockTransaction")
@RequiredArgsConstructor
public class StockTransactionController {
    private final StockTransactionService stockTransactionService;
    private final StockTransactionConverter stockTransactionConverter;

    @PostMapping("/list")
    public ResponsePage<StockTransactionVo> getStockTransactionByPage(@RequestBody QueryPageParam queryPageParam) {
        return stockTransactionService.getStockTransactionPageData(queryPageParam);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addStockTransaction(@RequestBody StockTransactionVo stockTransactionVo) {
        return stockTransactionService.addStockTransaction(stockTransactionVo);
    }

    @DeleteMapping("/{id}")
    public boolean removeStockTransactionById(@PathVariable Long id) {
        return stockTransactionService.removeById(id);
    }
}
