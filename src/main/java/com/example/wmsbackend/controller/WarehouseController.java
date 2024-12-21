package com.example.wmsbackend.controller;

import com.example.wmsbackend.converter.WarehouseConverterMapper;
import com.example.wmsbackend.entity.QueryPageParam;
import com.example.wmsbackend.entity.ResponsePage;
import com.example.wmsbackend.entity.Warehouse;
import com.example.wmsbackend.entity.vo.WarehouseVo;
import com.example.wmsbackend.service.WarehouseService;
import com.example.wmsbackend.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/warehouse")
@RequiredArgsConstructor
public class WarehouseController {
    private final WarehouseService warehouseService;

    @GetMapping
    public List<Warehouse> getWarehouseList() {
        return warehouseService.list();
    }

    @PostMapping("/list")
    public ResponsePage<WarehouseVo> getWarehouseByPage(@RequestBody QueryPageParam queryPageParam) {
        return warehouseService.getWarehousePageData(queryPageParam);
    }

    @PostMapping
    public boolean addWarehouse(@RequestBody WarehouseVo newWarehouse) {
        return warehouseService.save(WarehouseConverterMapper.INSTANCE.toPO(newWarehouse));
    }

    @PutMapping
    public boolean updateWarehouseById(@RequestBody WarehouseVo warehouseVo) {
        return warehouseService.updateWarehouseById(warehouseVo);
    }

    @GetMapping("/validate-warehousename/update")
    public ResponseEntity<ApiResponse> validateWarehouseName(@RequestParam String warehouseName, @RequestParam Long warehouseId) {
        System.out.println("Received warehouseName: " + warehouseName + ", warehouseId: " + warehouseId);
        return warehouseService.validateWarehouseIsExisted(warehouseName, warehouseId);
    }

    @DeleteMapping("/{id}")
    public boolean removeWarehouseById(@PathVariable Long id) {
        return warehouseService.removeById(id);
    }

    @GetMapping("/validate-warehousename/add")
    public ResponseEntity<ApiResponse> validateWarehouseName(@RequestParam String warehouseName) {
        System.out.println("Received warehouseName: " + warehouseName);
        return warehouseService.validateWarehouseIsExisted(warehouseName);
    }
}
