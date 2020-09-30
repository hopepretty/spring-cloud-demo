package com.pc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 服务降级：当客户调用A服务接口，因A调用B服务，B服务响应时间慢，影响了用户体验，进而降级
 * 服务熔断：当访问量达到一定地步，或者老是出现响应时长问题，继续下去会影响服务器性能，进而熔断相应接口访问，最主要
 *              的是它可以恢复链路调用；它其实与服务降级有关系，当失败调用到达一定阈值时，默认是5秒内20次调用失败，
 *              就会启动熔断机制。
 * 服务限流：
 *
 * @author pc
 */
@EnableHystrix
@EnableFeignClients
@SpringBootApplication
public class HystrixOrderBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixOrderBootApplication.class, args);
    }

}
