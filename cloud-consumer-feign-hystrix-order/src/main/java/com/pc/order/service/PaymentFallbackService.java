package com.pc.order.service;

import org.springframework.stereotype.Component;

/**
 * 在进行rpc调用时进行统一处理所有的rpc接口
 * @author pc
 * @Date 2020/9/12
 **/
@Component
public class PaymentFallbackService implements PaymentHystrixService {
    @Override
    public String paymentInfo_ok(Integer id) {
        return "PaymentFallbackService-paymentInfo_ok  熔断降级";
    }

    @Override
    public String paymentInfo_timeout(Integer id) {
        return "PaymentFallbackService-paymentInfo_timeout  熔断降级";
    }
}
