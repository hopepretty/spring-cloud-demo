package com.pc.payment.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.pc.payment.service.PayementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @DefaultProperties使用此注解进行全局服务降级配置
 * @author pc
 */
@DefaultProperties(defaultFallback = "global_timeout_handler")
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
//    @HystrixCommand(fallbackMethod = "paymentInfo_timeout_handler",
//            commandProperties ={@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")}
//    )
    @HystrixCommand
    @GetMapping("hystrix/timeout/{id}")
    public String paymentInfo_timeout(@PathVariable("id") Integer id) {
//        int value = 10/0;
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

    /**
     * 全局服务熔断降级配置
     * @return
     */
    public String global_timeout_handler() {
        return "服务正忙。。。。请稍后再试";
    }

    //===============服务熔断
    /**
     *  熔断
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), //是否开启断路器
            //请求次数  在下面的时间窗口期如果超过十次请求，会熔断,第一优先级
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            //时间窗口期
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
            //当上面的请求次数的失败率达到多少后跳闸  第二优先级
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")
    })
    @RequestMapping("hystrix/paymentCircuitBreaker/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if (id < 0) {
            throw new RuntimeException("*****id 不能负数");
        }
        String uid = UUID.randomUUID().toString();
        return Thread.currentThread().getName() + "\t 调用成功，流水号是：" + uid;
    }

    /**
     * 熔断处理
     * @return
     */
    public String paymentCircuitBreaker_fallback(Integer id) {
        return "id 不能为负数，请稍后再试";
    }


}
