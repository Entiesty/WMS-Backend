package com.example.wmsbackend.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteVo {
    private Long id;
    private String path;
    private String name;
    private String parentName;
    private String role;
}
