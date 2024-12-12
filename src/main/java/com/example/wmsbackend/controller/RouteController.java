package com.example.wmsbackend.controller;

import com.example.wmsbackend.entity.Route;
import com.example.wmsbackend.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/route")
public class RouteController {
    private final RouteService routeService;

    @GetMapping("/{role}")
    public List<Route> getUserRoutes(@PathVariable String role) {
        List<Route> routes = routeService.getRoutesByRole(role);
        return routeService.buildRouteTree(routes);
    }
}
