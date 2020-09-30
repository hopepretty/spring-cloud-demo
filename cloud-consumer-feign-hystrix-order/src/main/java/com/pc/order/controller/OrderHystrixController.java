package com.pc.order.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.pc.order.service.PaymentHystrixService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author pc
 */
@RestController
@RequestMapping("order")
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping("hystrix/ok/{id}")
    public String paymentInfo_ok(@PathVariable("id") Integer id) {
        return paymentHystrixService.paymentInfo_ok(id);
    }

    /**
     * 调用方服务降级
     *
     * 使用jmeter进行并发测试  当发起两万个并发到此接口，由于此接口占用了tomcat的工作线程数，
     * 导致其他正常的接口受损，这是不应该的   tomcat默认10个工作线程
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "paymentInfo_timeout_handler",
            commandProperties ={@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")}
    )
    @GetMapping("hystrix/timeout/{id}")
    public String paymentInfo_timeout(@PathVariable("id") Integer id) {
        String infoTimeout = null;
        try {
            TimeUnit.SECONDS.sleep(5000);
            infoTimeout = paymentHystrixService.paymentInfo_timeout(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return infoTimeout;
    }

    /**
     * 每个方法不推荐使用，会使代码冗余
     *
     * 服务降级熔断处理
     * @param id
     * @return
     */
    public String paymentInfo_timeout_handler(Integer id) {
        return "调用方 。。。服务正忙。。。。请稍后再试";
    }

    /**
     * 全局服务熔断降级配置
     * @param id
     * @return
     */
    public String global_timeout_handler(Integer id) {
        return "调用方 。。。服务正忙。。。。请稍后再试";
    }

}
