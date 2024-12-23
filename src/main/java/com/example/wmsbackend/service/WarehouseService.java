package com.example.wmsbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.wmsbackend.entity.QueryPageParam;
import com.example.wmsbackend.entity.ResponsePage;
import com.example.wmsbackend.entity.Warehouse;
import com.example.wmsbackend.entity.vo.WarehouseVo;
import com.example.wmsbackend.util.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WarehouseService extends IService<Warehouse> {
    ResponsePage<WarehouseVo> getWarehousePageData(QueryPageParam queryPageParam);
    Warehouse getWarehouseByWarehouseName(String warehouseName);
    boolean updateWarehouseById(WarehouseVo warehouseVo);
    ResponseEntity<ApiResponse> validateWarehouseIsExisted(String warehouseName, Long id);
    ResponseEntity<ApiResponse> validateWarehouseIsExisted(String warehouseName);
    List<Warehouse> findWarehousesByIds(List<Long> warehouseIds);
}
