package com.pc.payment.dao;

import com.pc.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 支付接口
 */
@Mapper
public interface PaymentDao {

    /**
     * 创建
     * @param Payment
     * @return
     */
    public int create(Payment Payment);

    /**
     * 根据id获取
     * @param id
     * @return
     */
    public Payment getPaymentById(@Param("id") Long id);

}
