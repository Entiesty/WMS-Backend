package com.example.wmsbackend.service.impl;

import com.example.wmsbackend.DTO.DailyInStockData;
import com.example.wmsbackend.DTO.DailyOutStockData;
import com.example.wmsbackend.mapper.StockTransactionMapper;
import com.example.wmsbackend.service.StatisticalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticalServiceImpl implements StatisticalService {

    private final StockTransactionMapper stockTransactionMapper;

    @Override
    public List<DailyInStockData> getDailyInStockData(LocalDate startDate, LocalDate endDate) {
        return stockTransactionMapper.getDailyInStockData(startDate, endDate);
    }

    @Override
    public List<DailyOutStockData> getDailyOutStockData(LocalDate startDate, LocalDate endDate) {
        return stockTransactionMapper.getDailyOutStockData(startDate, endDate);
    }
}

