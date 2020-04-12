package com.pc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author pc
 */
@EnableEurekaClient
@SpringBootApplication
public class OrderBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderBootApplication.class, args);
    }

}
