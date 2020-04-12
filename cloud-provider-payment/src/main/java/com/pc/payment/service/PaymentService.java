package com.pc.payment.service;

import com.pc.entities.Payment;
import org.apache.ibatis.annotations.Param;

/**
 *
 */
public interface PaymentService {

    /**
     * 创建
     * @param payment
     * @return
     */
    public int create(Payment payment);

    /**
     * 根据id获取
     * @param id
     * @return
     */
    public Payment getPaymentById(@Param("id") Long id);

}
