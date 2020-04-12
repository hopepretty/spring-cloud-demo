package com.pc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 *
 * 使用注解将此服务注册为一个  Eureka 服务端
 * @author pc
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerCluster01Application {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerCluster01Application.class, args);
    }

}
