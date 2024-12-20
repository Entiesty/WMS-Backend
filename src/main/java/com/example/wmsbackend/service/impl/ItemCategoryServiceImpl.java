package com.example.wmsbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wmsbackend.entity.ItemCategory;
import com.example.wmsbackend.entity.QueryPageParam;
import com.example.wmsbackend.entity.ResponsePage;
import com.example.wmsbackend.mapper.ItemCategoryMapper;
import com.example.wmsbackend.service.ItemCategoryService;
import com.example.wmsbackend.util.ApiResponse;
import com.example.wmsbackend.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemCategoryServiceImpl extends ServiceImpl<ItemCategoryMapper, ItemCategory> implements ItemCategoryService {
    private final ItemCategoryMapper itemCategoryMapper;

    @Override
    public ItemCategory getItemCategoryByItemCategoryName(String itemCategoryName) {
        LambdaQueryWrapper<ItemCategory> itemCategoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        itemCategoryLambdaQueryWrapper.eq(ItemCategory::getItemCategoryName, itemCategoryName);

        return this.getOne(itemCategoryLambdaQueryWrapper);
    }

    @Override
    public ResponsePage<ItemCategory> getItemCategoryPageData(QueryPageParam queryPageParam) {
        Page<ItemCategory> page = new Page<>(queryPageParam.getCurrent(), queryPageParam.getSize());
        // 使用 PaginationUtil 获取分页数据
        return PaginationUtil.getPaginatedData(page,
                (p) -> itemCategoryMapper.selectPage(p, null),   // 查询方法，获取 User 数据
                (itemCategory) -> itemCategory
        );
    }

    @Override
    public ResponseEntity<ApiResponse> validateItemCategoryIsExisted(String newItemCategoryName, Long id) {
        String currentItemCategory = this.getById(id).getItemCategoryName();
        if (currentItemCategory.equals(newItemCategoryName)) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("货品类名唯一！", true));
        } else if (this.getItemCategoryByItemCategoryName(newItemCategoryName) == null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("货品类名唯一！", true));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("货品类名已存在！", false));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> validateItemCategoryIsExisted(String itemCategoryName) {
        if (this.getItemCategoryByItemCategoryName(itemCategoryName) == null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("货品类名唯一！", true));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("货品类名已存在！", false));
        }
    }
}
