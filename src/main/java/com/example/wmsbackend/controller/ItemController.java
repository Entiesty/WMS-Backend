package com.example.wmsbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.wmsbackend.entity.Item;
import com.example.wmsbackend.entity.QueryPageParam;
import com.example.wmsbackend.entity.ResponsePage;
import com.example.wmsbackend.entity.Warehouse;
import com.example.wmsbackend.entity.vo.ItemVo;
import com.example.wmsbackend.service.ItemService;
import com.example.wmsbackend.service.WarehouseService;
import com.example.wmsbackend.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final WarehouseService warehouseService;

    @GetMapping
    public List<Item> getItemList() {
        return itemService.list();
    }

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

    @GetMapping("/for-warehouse/{itemName}")
    public List<String> getWarehousesForItemName(@PathVariable String itemName) {
        // 去掉路径变量中的括号及其中内容
        String cleanedItemName = removeParentheses(itemName);

        // 查询数据库中的 Item 列表
        List<Item> items = itemService.findItemsByName(cleanedItemName);

        // 获取每个 Item 对应的 warehouseId
        List<Long> warehouseIds = items.stream()
                .map(Item::getWarehouseId)
                .collect(Collectors.toList());

        // 根据 warehouseId 列表获取对应的 Warehouse 对象
        List<Warehouse> warehouses = warehouseService.findWarehousesByIds(warehouseIds);

        // 获取每个 Warehouse 对象的 warehouseName 并返回
        return warehouses.stream()
                .map(Warehouse::getWarehouseName)
                .collect(Collectors.toList());
    }

    // 用于去除字符串末尾的括号及其中内容
    private String removeParentheses(String input) {
        // 正则：去掉末尾括号及括号里的内容
        return input.replaceAll("\\s?\\([^)]+\\)$", "");
    }
}
