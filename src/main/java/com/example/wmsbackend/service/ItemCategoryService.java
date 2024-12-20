package com.example.wmsbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.wmsbackend.entity.ItemCategory;
import com.example.wmsbackend.entity.QueryPageParam;
import com.example.wmsbackend.entity.ResponsePage;
import com.example.wmsbackend.entity.User;
import com.example.wmsbackend.util.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface ItemCategoryService extends IService<ItemCategory> {
    ItemCategory getItemCategoryByItemCategoryName(String itemCategoryName);
    ResponsePage<ItemCategory> getItemCategoryPageData(QueryPageParam queryPageParam);

    ResponseEntity<ApiResponse> validateItemCategoryIsExisted(String itemCategoryName, Long id);
    ResponseEntity<ApiResponse> validateItemCategoryIsExisted(String itemCategoryName);
}
