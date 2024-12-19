package com.example.wmsbackend.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseVo {
    private Long id;
    private String warehouseName;
    private String location;
    private String createdAt;
    private String updatedAt;
    private List<String> roles;
}
