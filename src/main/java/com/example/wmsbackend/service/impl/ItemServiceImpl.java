package com.example.wmsbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wmsbackend.entity.Item;
import com.example.wmsbackend.entity.QueryPageParam;
import com.example.wmsbackend.entity.ResponsePage;
import com.example.wmsbackend.entity.vo.ItemVo;
import com.example.wmsbackend.mapper.ItemMapper;
import com.example.wmsbackend.service.ItemCategoryService;
import com.example.wmsbackend.service.ItemService;
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
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {

    private final ItemMapper itemMapper;
    private final ItemCategoryService itemCategoryService;
    private final WarehouseService warehouseService;

    @Override
    public boolean addItem(ItemVo itemVo) {
        return this.save(itemVoToPo(itemVo));
    }

    private Item itemVoToPo(ItemVo itemVo) {
        Item item = new Item();
        if(itemVo.getId() != null){
        item.setId(itemVo.getId());
        }
        item.setItemName(itemVo.getItemName());
        item.setPrice(itemVo.getPrice());
        item.setStock(itemVo.getStock());
        item.setImageUrl(itemVo.getImageUrl());
        item.setItemCategoryId(itemCategoryService.getItemCategoryByItemCategoryName(itemVo.getItemCategoryName()).getId());
        item.setWarehouseId(warehouseService.getWarehouseByWarehouseName(itemVo.getWarehouseName()).getId());

        return item;
    }

    @Override
    public boolean updateItemById(ItemVo itemVo) {
        return this.updateById(itemVoToPo(itemVo));
    }

    @Override
    public ResponsePage<ItemVo> getItemPageData(QueryPageParam queryPageParam) {
        Page<Item> page = new Page<>(queryPageParam.getCurrent(), queryPageParam.getSize());

        return PaginationUtil.getPaginatedData(page,
                (p) -> itemMapper.selectPage(p, null),  // 获取 Item 数据
                item -> {                               // 转换 Item 到 ItemVo
                    ItemVo itemVo = new ItemVo();
                    itemVo.setId(item.getId());
                    itemVo.setItemName(item.getItemName());
                    itemVo.setPrice(item.getPrice());  // 转换为 String
                    itemVo.setStock(item.getStock()); // 转换为 String
                    itemVo.setImageUrl(item.getImageUrl());
                    itemVo.setItemCategoryName(itemCategoryService.getById(item.getItemCategoryId()).getItemCategoryName());
                    itemVo.setWarehouseName(warehouseService.getById(item.getWarehouseId()).getWarehouseName());
                    return itemVo;
                }
        );
    }


    @Override
    public ResponseEntity<ApiResponse> validateItemNameIsExisted(String itemName, Long id) {
        String currentItemName = this.getById(id).getItemName();
        if (currentItemName.equals(itemName)) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("商品名称唯一！", true));
        } else if (this.getItemByItemName(itemName) == null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("商品名称唯一！", true));
        }
        else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("商品名称已存在！", false));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> validateItemNameIsExisted(String itemName) {
        if (this.getItemByItemName(itemName) == null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("商品名称唯一！", true));
        }
        else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("商品名称已存在！", false));
        }
    }

    private Item getItemByItemName(String itemName) {
        LambdaQueryWrapper<Item> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Item::getItemName, itemName);
        return this.getOne(wrapper);
    }

    // 根据物品名称查询Item列表，去掉括号中的内容
    public List<Item> findItemsByName(String cleanedItemName) {
        // 使用 MyBatis-Plus 提供的 QueryWrapper 进行查询
        return this.list(new QueryWrapper<Item>().like("item_name", cleanedItemName).select("id", "item_name", "warehouse_id"));
    }
}
