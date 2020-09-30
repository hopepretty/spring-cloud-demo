package com.pc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * http://localhost:9001/hystrix
 * HystrixDashboard
 * @author pc
 * @Date 2020/9/12
 **/
@SpringBootApplication
@EnableHystrixDashboard
public class HystrixApplicationHystrixApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixApplicationHystrixApplication.class, args);
    }

}
