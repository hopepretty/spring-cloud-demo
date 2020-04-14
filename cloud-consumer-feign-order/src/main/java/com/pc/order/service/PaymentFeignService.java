package com.pc.order.service;

import com.pc.entities.JsonResult;
import com.pc.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * feign接口
 */
@Component
@FeignClient(value = "CLOUD-PROVIDER-PAYMENT", path = "cloud-provider-payment")
public interface PaymentFeignService {

    /**
     * 创建
     * @param id
     * @return
     */
    @GetMapping("payment/getPaymentById/{id}")
    public JsonResult<Payment> getPaymentById(@PathVariable("id") Long id);

    /**
     * feign超时测试
     * @return
     */
    @GetMapping("payment/feign/timeout")
    public String feignTimeout();

}
