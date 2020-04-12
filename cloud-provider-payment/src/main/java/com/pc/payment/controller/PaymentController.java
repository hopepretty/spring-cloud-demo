package com.pc.payment.controller;

import com.pc.entities.JsonResult;
import com.pc.entities.Payment;
import com.pc.payment.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author pc
 */
@Slf4j
@RestController
@RequestMapping("payment")
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    /**
     * 创建
     * @param payment
     * @return
     */
    @PostMapping("create")
    public JsonResult<Long> create(@RequestBody Payment payment) {
        System.out.println("当前服务端口为：" + serverPort);
        JsonResult<Long> jsonResult = new JsonResult<>();
        System.out.println("sss");
        int iid = paymentService.create(payment);
        log.info("插入结果：" + iid);

        if (iid > 0) {
            jsonResult.setMessage("插入成功");
        } else {
            jsonResult.setSuccess(false);
            jsonResult.setMessage("插入失败");
        }
        return jsonResult;
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("getPaymentById/{id}")
    public JsonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        System.out.println("当前服务端口为：" + serverPort);
        JsonResult<Payment> jsonResult = new JsonResult<Payment>();

        Payment payment = paymentService.getPaymentById(id);
        log.info("查询结果：" + payment);

        if (payment != null) {
            jsonResult.setData(payment);
            jsonResult.setMessage("查询成功");
        } else {
            jsonResult.setSuccess(false);
            jsonResult.setMessage("查询失败");
        }
        return jsonResult;
    }

    /**
     * 通过服务发现获取所有的信息
     * @return
     */
    @GetMapping("discovery")
    public Object discovery() {
        //获取所有的服务名
        List<String> services = discoveryClient.getServices();
        System.out.println(services);

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PROVIDER-PAYMENT");
        System.out.println(instances);
        return instances;
    }

}
