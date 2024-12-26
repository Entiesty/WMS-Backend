package com.example.wmsbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.wmsbackend.entity.QueryPageParam;
import com.example.wmsbackend.entity.ResponsePage;
import com.example.wmsbackend.entity.Route;
import com.example.wmsbackend.entity.vo.RouteVo;
import com.example.wmsbackend.util.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RouteService extends IService<Route> {
    List<Route> getRoutesByRole(String role);
    List<Route> buildRouteTree(List<Route> routes);
    ResponsePage<RouteVo> getRoutePageData(QueryPageParam queryPageParam);
    ResponseEntity<ApiResponse> validateRouteNameIsExisted(String routeName, Long id);
    ResponseEntity<ApiResponse> validateRouteNameIsExisted(String routeName);
    RouteVo routePoToVo(Route route);
    public Route routeVoToPo(RouteVo routeVo);
    public List<Route> getRouteList();
    boolean addRoute(RouteVo routeVo);
    boolean updateRouteById(RouteVo routeVo);
}
