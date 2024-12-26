package com.example.wmsbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wmsbackend.entity.QueryPageParam;
import com.example.wmsbackend.entity.ResponsePage;
import com.example.wmsbackend.entity.Route;
import com.example.wmsbackend.entity.vo.RouteVo;
import com.example.wmsbackend.mapper.RouteMapper;
import com.example.wmsbackend.service.RouteService;
import com.example.wmsbackend.util.ApiResponse;
import com.example.wmsbackend.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl extends ServiceImpl<RouteMapper, Route> implements RouteService {
    private final RouteMapper routeMapper;

    @Override
    public List<Route> getRoutesByRole(String role) {

        LambdaQueryWrapper<Route> routeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(role.equals("super_admin")) {
            return this.list();
        } else {
            routeLambdaQueryWrapper.eq(Route::getRole, role);
            return this.list(routeLambdaQueryWrapper);
        }
    }

    @Override
    public List<Route> buildRouteTree(List<Route> routes) {
        Map<Long, Route> routeMap = new HashMap<>();
        for (Route route : routes) {
            routeMap.put(route.getId(), route);
        }

        List<Route> routeTree = new ArrayList<>();
        for (Route route : routes) {
            if (route.getParentId() == null) {
                routeTree.add(route);
            } else {
                Route parentRoute = routeMap.get(route.getParentId());
                if (parentRoute != null) {
                    parentRoute.getChildren().add(route);
                }
            }
        }
        return routeTree;
    }

    @Override
    public RouteVo routePoToVo(Route route) {
        RouteVo routeVo = new RouteVo();
        routeVo.setId(route.getId());
        routeVo.setPath(route.getPath());
        routeVo.setName(route.getName());
        if (route.getParentId() != null) {
            routeVo.setParentName(this.getById(route.getParentId()).getName());
        }
        routeVo.setRole(route.getRole());
        return routeVo;
    }

    @Override
    public Route routeVoToPo(RouteVo routeVo) {
        Route route = new Route();
        route.setId(routeVo.getId());
        route.setPath(routeVo.getPath());
        route.setName(routeVo.getName());
        route.setParentId(this.getRouteByName(routeVo.getParentName()).getId());
        route.setRole(routeVo.getRole());
        return route;
    }

    @Override
    public List<Route> getRouteList() {
        return this.list();
    }

    @Override
    public ResponsePage<RouteVo> getRoutePageData(QueryPageParam queryPageParam) {
        Page<Route> routePage = new Page<>(queryPageParam.getCurrent(), queryPageParam.getSize());

        return PaginationUtil.getPaginatedData(routePage,
                (p) -> routeMapper.selectPage(p, null),
                this::routePoToVo
        );
    }

    @Override
    public ResponseEntity<ApiResponse> validateRouteNameIsExisted(String routeName, Long id) {
        String currentRouteName = this.getById(id).getName();

        // 如果路由名称没有改变，返回唯一
        if (currentRouteName.equals(routeName)) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("路由名称唯一！", true));
        }
        // 如果查询不到路由名称，表示该名称可以使用
        else if (this.getRouteByName(routeName) == null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("路由名称唯一！", true));
        }
        // 如果已存在，则返回冲突错误
        else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("路由名称已存在！", false));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> validateRouteNameIsExisted(String routeName) {
        // 查询是否已经有该路由名称
        if (this.getRouteByName(routeName) == null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("路由名称唯一！", true));
        }
        // 如果已存在，返回冲突错误
        else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("路由名称已存在！", false));
        }
    }

    // 根据路由名称获取路由对象
    private Route getRouteByName(String routeName) {
        LambdaQueryWrapper<Route> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Route::getName, routeName);
        return this.getOne(wrapper);
    }

    public boolean addRoute(RouteVo routeVo) {
        Route route = this.routeVoToPo(routeVo);
        return this.save(route);
    }

    @Override
    public boolean updateRouteById(RouteVo routeVo) {
        Route route = this.routeVoToPo(routeVo);
        return this.updateById(route);
    }
}
