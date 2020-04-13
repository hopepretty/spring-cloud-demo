package com.pc.order.controller;

import com.pc.entities.JsonResult;
import com.pc.entities.Payment;
import com.pc.order.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 使用feign进行调用
 * @author pc
 */
@RestController
@RequestMapping("order")
@Slf4j
public class OrderFeignController {

    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping("getPaymentById/{id}")
    public JsonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        JsonResult<Payment> jsonResult = paymentFeignService.getPaymentById(id);
        return jsonResult;
    }

}
