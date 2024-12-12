package com.example.wmsbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.wmsbackend.entity.Route;

import java.util.List;

public interface RouteService extends IService<Route> {
    List<Route> getRoutesByRole(String role);
    List<Route> buildRouteTree(List<Route> routes);
}
