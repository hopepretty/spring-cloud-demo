package com.pc;

import com.pc.myrule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * @author pc
 */
@RibbonClient(name = "CLOUD-PROVIDER-PAYMENT", configuration = MySelfRule.class)
@EnableEurekaClient
@SpringBootApplication
public class OrderBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderBootApplication.class, args);
    }

}
