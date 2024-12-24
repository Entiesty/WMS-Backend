package com.example.wmsbackend.controller;

import com.example.wmsbackend.entity.QueryPageParam;
import com.example.wmsbackend.entity.ResponsePage;
import com.example.wmsbackend.entity.vo.StockTransactionRankingVo;
import com.example.wmsbackend.entity.vo.StockTransactionVo;
import com.example.wmsbackend.service.StockTransactionService;
import com.example.wmsbackend.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/stockTransaction")
@RequiredArgsConstructor
public class StockTransactionController {
    private final StockTransactionService stockTransactionService;

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

    @GetMapping("/top-stock-transactions")
    public List<StockTransactionRankingVo> getTopStockTransactions(
            @RequestParam String startDate, @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return stockTransactionService.getStockTransactionRanking(start, end);
    }
}
