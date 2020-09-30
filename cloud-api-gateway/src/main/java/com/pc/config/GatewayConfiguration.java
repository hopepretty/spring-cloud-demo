package com.pc.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * gateway路由配置
 * @author pc
 * @Date 2020/9/13
 **/
@Configuration
public class GatewayConfiguration {

    /**
     * 第二种配置路由方式
     * @param routeLocatorBuilder
     * @return
     */
    @Bean
    public RouteLocator custormRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        routes.route("path_route_news", r -> r.path("/guonei").uri("http://news.baidu.com/guonei")).build();
        return routes.build();
    }

}
