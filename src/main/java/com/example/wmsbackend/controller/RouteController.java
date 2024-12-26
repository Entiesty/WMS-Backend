package com.example.wmsbackend.controller;

import com.example.wmsbackend.entity.QueryPageParam;
import com.example.wmsbackend.entity.ResponsePage;
import com.example.wmsbackend.entity.Route;
import com.example.wmsbackend.entity.vo.RouteVo;
import com.example.wmsbackend.service.RouteService;
import com.example.wmsbackend.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/route")
public class RouteController {
    private final RouteService routeService;

    @GetMapping
    public List<Route> getRouteList() {
        return routeService.list();
    }

    @GetMapping("/{role}")
    public List<Route> getUserRoutes(@PathVariable String role) {
        List<Route> routes = routeService.getRoutesByRole(role);
        return routeService.buildRouteTree(routes);
    }

    @PostMapping("/list")
    public ResponsePage<RouteVo> getRouteByPage(@RequestBody QueryPageParam queryPageParam) {
        return routeService.getRoutePageData(queryPageParam);
    }

    @GetMapping("/validate-route-name/add")
    public ResponseEntity<ApiResponse> validateRouteNameForAdd(@RequestParam String routeName) {
        return routeService.validateRouteNameIsExisted(routeName);
    }

    @GetMapping("/validate-route-name/update")
    public ResponseEntity<ApiResponse> validateRouteNameForUpdate(@RequestParam String routeName, @RequestParam Long routeId) {
        return routeService.validateRouteNameIsExisted(routeName, routeId);
    }

    @PutMapping
    public boolean updateRouteById(@RequestBody Route route) {
        return routeService.updateById(route);
    }

    @DeleteMapping("/{id}")
    public boolean DeleteRouteById(@PathVariable Long id) {
        return routeService.removeById(id);
    }

    @PostMapping
    public boolean addRoute(@RequestBody RouteVo routeVo) {
        return routeService.addRoute(routeVo);
    }

}
