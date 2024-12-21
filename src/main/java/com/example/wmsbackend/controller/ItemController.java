package com.example.wmsbackend.controller;

import com.example.wmsbackend.entity.Item;
import com.example.wmsbackend.entity.QueryPageParam;
import com.example.wmsbackend.entity.ResponsePage;
import com.example.wmsbackend.entity.vo.ItemVo;
import com.example.wmsbackend.service.ItemService;
import com.example.wmsbackend.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/list")
    public ResponsePage<ItemVo> getItemByPage(@RequestBody QueryPageParam queryPageParam) {
        return itemService.getItemPageData(queryPageParam);
    }

    @PostMapping
    public boolean addItem(@RequestBody ItemVo itemVo) {
        return itemService.addItem(itemVo);
    }

    @GetMapping("/validate-item-name/add")
    public ResponseEntity<ApiResponse> validateItemName(@RequestParam String itemName) {
        return itemService.validateItemNameIsExisted(itemName);
    }

    @GetMapping("/validate-item-name/update")
    public ResponseEntity<ApiResponse> validateItemName(@RequestParam String itemName, @RequestParam Long itemId) {
        return itemService.validateItemNameIsExisted(itemName, itemId);
    }

    @PutMapping
    public boolean updateItemById(@RequestBody ItemVo itemVo) {
        return itemService.updateItemById(itemVo);
    }

    @DeleteMapping("/{id}")
    public boolean removeItemById(@PathVariable Long id) {
        return itemService.removeById(id);
    }
}
