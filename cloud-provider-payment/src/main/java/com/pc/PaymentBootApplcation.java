package com.pc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 引导类
 * @author pc
 */
@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
public class PaymentBootApplcation {

    public static void main(String[] args) {
        SpringApplication.run(PaymentBootApplcation.class, args);
    }

}
