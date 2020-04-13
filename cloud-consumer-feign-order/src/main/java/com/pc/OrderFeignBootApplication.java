package com.pc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author pc
 *
 * 开启feign
 */
@EnableFeignClients
@SpringBootApplication
public class OrderFeignBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderFeignBootApplication.class, args);
    }

}
