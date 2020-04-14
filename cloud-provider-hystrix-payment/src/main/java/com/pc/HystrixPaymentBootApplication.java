package com.pc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author pc
 */
@EnableCircuitBreaker
@EnableEurekaClient
@SpringBootApplication
public class HystrixPaymentBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixPaymentBootApplication.class, args);
    }

}
