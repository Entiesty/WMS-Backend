package com.example.wmsbackend;

import com.example.wmsbackend.entity.Route;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class WmsBackendApplicationTests {
    @Test
    public void test1() {
        List<Route> routes = new ArrayList<>();
        Route route1 = new Route(1L, "/", "home", null, "admin", new ArrayList<>());
        Route route2 = new Route(2L, "/user", "user", 1L, "admin", new ArrayList<>());
        Route route3 = new Route(3L, "/user/list", "userList", 2L, "admin", new ArrayList<>());

        List<Route> routeTree = new ArrayList<>();
        routes.add(route1);
        routeTree.add(routes.get(0));

        route1.getChildren().add(route2);

        System.out.println(routes);
        System.out.println(route1);
        System.out.println(routeTree);
    }

}
