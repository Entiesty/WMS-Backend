package com.example.wmsbackend.controller;

import com.example.wmsbackend.entity.ItemCategory;
import com.example.wmsbackend.entity.QueryPageParam;
import com.example.wmsbackend.entity.ResponsePage;
import com.example.wmsbackend.service.ItemCategoryService;
import com.example.wmsbackend.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item-category")
@RequiredArgsConstructor
public class ItemCategoryController {
    private final ItemCategoryService itemCategoryService;

    @PostMapping("/list")
    public ResponsePage<ItemCategory> getItemCategoryByPage(@RequestBody QueryPageParam queryPageParam) {
        return itemCategoryService.getItemCategoryPageData(queryPageParam);
    }

    @PostMapping
    public boolean addItemCategory(@RequestBody ItemCategory itemCategory) {
        return itemCategoryService.save(itemCategory);
    }

    @GetMapping("/validate-item-category-name/add")
    public ResponseEntity<ApiResponse> validateItemCategoryName(@RequestParam String itemCategoryName) {
        System.out.println("Received ItemCategoryName: " + itemCategoryName);
        return itemCategoryService.validateItemCategoryIsExisted(itemCategoryName);
    }

    @GetMapping("/validate-item-category-name/update")
    public ResponseEntity<ApiResponse> validateItemCategoryName(@RequestParam String itemCategoryName, @RequestParam Long itemCategoryId) {
        System.out.println("Received username: " + itemCategoryName + ", userId: " + itemCategoryId);
        return itemCategoryService.validateItemCategoryIsExisted(itemCategoryName, itemCategoryId);
    }

    @PutMapping
    public boolean updateItemCategoryById(@RequestBody ItemCategory itemCategory) {
        return itemCategoryService.updateById(itemCategory);
    }

    @DeleteMapping("/{id}")
    public boolean removeItemCategoryById(@PathVariable Long id) {
        return itemCategoryService.removeById(id);
    }
}
