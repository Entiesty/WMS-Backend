package com.example.wmsbackend.controller;

import com.example.wmsbackend.DTO.DailyInStockData;
import com.example.wmsbackend.DTO.DailyOutStockData;
import com.example.wmsbackend.service.StatisticalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticalController {

    private final StatisticalService statisticalService;

    @GetMapping("/daily-in-stock")
    public List<DailyInStockData> getDailyInStockData(@RequestParam("startDate") String startDate,
                                                      @RequestParam("endDate") String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return statisticalService.getDailyInStockData(start, end);
    }

    @GetMapping("/daily-out-stock")
    public List<DailyOutStockData> getDailyOutStockData(@RequestParam("startDate") String startDate,
                                                        @RequestParam("endDate") String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return statisticalService.getDailyOutStockData(start, end);
    }
}

