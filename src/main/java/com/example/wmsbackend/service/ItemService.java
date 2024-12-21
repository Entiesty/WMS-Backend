package com.example.wmsbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.wmsbackend.entity.Item;
import com.example.wmsbackend.entity.QueryPageParam;
import com.example.wmsbackend.entity.ResponsePage;
import com.example.wmsbackend.entity.vo.ItemVo;
import com.example.wmsbackend.util.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface ItemService extends IService<Item> {
    boolean addItem(ItemVo itemVo);
    boolean updateItemById(ItemVo itemVo);
    ResponsePage<ItemVo> getItemPageData(QueryPageParam queryPageParam);
    ResponseEntity<ApiResponse> validateItemNameIsExisted(String itemName, Long id);

    ResponseEntity<ApiResponse> validateItemNameIsExisted(String itemName);
}
