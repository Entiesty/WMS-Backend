package com.example.wmsbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wmsbackend.converter.WarehouseConverterMapper;
import com.example.wmsbackend.entity.QueryPageParam;
import com.example.wmsbackend.entity.ResponsePage;
import com.example.wmsbackend.entity.Warehouse;
import com.example.wmsbackend.entity.vo.WarehouseVo;
import com.example.wmsbackend.mapper.WarehouseMapper;
import com.example.wmsbackend.service.WarehouseService;
import com.example.wmsbackend.util.ApiResponse;
import com.example.wmsbackend.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl extends ServiceImpl<WarehouseMapper, Warehouse> implements WarehouseService {
    private final WarehouseMapper warehouseMapper;
    @Override
    public ResponsePage<WarehouseVo> getWarehousePageData(QueryPageParam queryPageParam) {
        Page<Warehouse> page = new Page<>(queryPageParam.getCurrent(), queryPageParam.getSize());

        return PaginationUtil.getPaginatedData(page,
                (p) -> warehouseMapper.selectPage(p, null), // 查询仓库数据
                WarehouseConverterMapper.INSTANCE::toVO // 将 Warehouse 转换为 WarehouseVo
        );
    }

    @Override
    public Warehouse getWarehouseByWarehouseName(String warehouseName) {
        LambdaQueryWrapper<Warehouse> warehouseLambdaQueryWrapper = new LambdaQueryWrapper<>();
        warehouseLambdaQueryWrapper.eq(Warehouse::getWarehouseName, warehouseName);

        return this.getOne(warehouseLambdaQueryWrapper);
    }

    @Override
    public boolean updateWarehouseById(WarehouseVo warehouseVo) {
        Warehouse warehouse = WarehouseConverterMapper.INSTANCE.toPO(warehouseVo);

        return this.updateById(warehouse);
    }

    @Override
    public ResponseEntity<ApiResponse> validateWarehouseIsExisted(String newWarehouseName, Long id) {
        String currentWarehouseName = this.getById(id).getWarehouseName();
        if (currentWarehouseName.equals(newWarehouseName)) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("仓库名唯一！", true));
        } else if (this.getWarehouseByWarehouseName(newWarehouseName) == null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("仓库名唯一！", true));
        }
        else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("仓库名已存在！", false));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> validateWarehouseIsExisted(String warehouseName) {
        if (this.getWarehouseByWarehouseName(warehouseName) == null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("仓库名唯一！", true));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("仓库名已存在！", false));
        }
    }

    @Override
    public List<Warehouse> findWarehousesByIds(List<Long> warehouseIds) {
        return this.list(new QueryWrapper<Warehouse>().in("id", warehouseIds));
    }

}
