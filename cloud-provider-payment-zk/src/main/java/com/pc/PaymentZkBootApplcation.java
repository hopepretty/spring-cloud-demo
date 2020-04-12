package com.pc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 引导类
 *
 * 当我们使用 consul或者 zookeeper进行服务发现都是通过@EnableDiscoveryClient进行配置
 * @author pc
 */
@EnableDiscoveryClient
@SpringBootApplication
public class PaymentZkBootApplcation {

    public static void main(String[] args) {
        SpringApplication.run(PaymentZkBootApplcation.class, args);
    }

}
