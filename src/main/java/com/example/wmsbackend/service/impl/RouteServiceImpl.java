package com.example.wmsbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wmsbackend.entity.Route;
import com.example.wmsbackend.mapper.RouteMapper;
import com.example.wmsbackend.service.RouteService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RouteServiceImpl extends ServiceImpl<RouteMapper, Route> implements RouteService {
    @Override
    public List<Route> getRoutesByRole(String role) {
        LambdaQueryWrapper<Route> routeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        routeLambdaQueryWrapper.eq(Route::getRole, role);

        return this.list(routeLambdaQueryWrapper);
    }

    @Override
    public List<Route> buildRouteTree(List<Route> routes) {
        Map<Long, Route> routeMap = new HashMap<>();
        for(Route route : routes) {
            routeMap.put(route.getId(), route);
        }

        List<Route> routeTree = new ArrayList<>();
        for(Route route: routes) {
            if(route.getParentId() == null) {
                routeTree.add(route);
            } else {
                Route parentRoute = routeMap.get(route.getParentId());
                if(parentRoute != null) {
                    parentRoute.getChildren().add(route);
                }
            }
        }
        return routeTree;
    }
}
