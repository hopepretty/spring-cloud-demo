package com.pc.order.controller;

import com.pc.order.service.PaymentHystrixService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

    @GetMapping("hystrix/timeout/{id}")
    public String paymentInfo_timeout(@PathVariable("id") Integer id) {
        return paymentHystrixService.paymentInfo_timeout(id);
    }

}
