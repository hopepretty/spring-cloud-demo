package com.pc.payment.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * hytrix服务降级、熔断与限流测试
 * @author pc
 */
@Service
public class PayementService {

    public String paymentInfo_ok(Integer id) {
        return "线程名：" + Thread.currentThread().getName() + " paymentInfo_ok, id=" + id + "正常";
    }

    public String paymentInfo_Timeout(Integer id) {
        int timeout = 5;
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程名：" + Thread.currentThread().getName() + " paymentInfo_Timeout, id=" + id + "超时";
    }

}
