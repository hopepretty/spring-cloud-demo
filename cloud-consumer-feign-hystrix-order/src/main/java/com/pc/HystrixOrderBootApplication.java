package com.pc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author pc
 */
@EnableFeignClients
@SpringBootApplication
public class HystrixOrderBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixOrderBootApplication.class, args);
    }

}
