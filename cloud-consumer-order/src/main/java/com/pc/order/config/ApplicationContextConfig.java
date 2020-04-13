package com.pc.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * spring配置
 * @author pc
 */
@Configuration
public class ApplicationContextConfig {

    /**
     * 配置restTemplate
     *
     * 使用注解@LoadBalanced 进行负载均衡配置
     * 使用 Ribbon进行负载均衡的,不需要关心服务的ip与端口
     * @return
     */
//    @LoadBalanced   使用自定义负载均衡策略
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
