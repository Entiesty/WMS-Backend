package com.example.wmsbackend.service;

import com.example.wmsbackend.DTO.DailyInStockData;
import com.example.wmsbackend.DTO.DailyOutStockData;

import java.time.LocalDate;
import java.util.List;

public interface StatisticalService {
    List<DailyInStockData> getDailyInStockData(LocalDate startDate, LocalDate endDate);
    List<DailyOutStockData> getDailyOutStockData(LocalDate startDate, LocalDate endDate);
}
