package com.pc.payment.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.pc.payment.service.PayementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author pc
 */
@RestController
@RequestMapping("payment")
public class PaymentController {

    private AtomicInteger atomicInteger_ok = new AtomicInteger(0);

    private AtomicInteger atomicInteger_timeout = new AtomicInteger(0);

    @Resource
    private PayementService payementService;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("hystrix/ok/{id}")
    public String paymentInfo_ok(@PathVariable("id") Integer id) {
        for (;;) {
            int current = atomicInteger_ok.get();
            if (atomicInteger_ok.compareAndSet(current, current + 1)) {
                System.out.println("paymentInfo_ok==" + current);
                break;
            }
        }
        return payementService.paymentInfo_ok(id);
    }

    /**
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
        int value = 10/0;
        for (;;) {
            int current = atomicInteger_timeout.get();
            if (atomicInteger_timeout.compareAndSet(current, current + 1)) {
                System.out.println("paymentInfo_timeout==" + current);
                break;
            }
        }
        return payementService.paymentInfo_Timeout(id);
    }

    /**
     * 服务降级熔断处理
     * @param id
     * @return
     */
    public String paymentInfo_timeout_handler(Integer id) {
        return "服务正忙。。。。请稍后再试";
    }


}
