package com.pc.order.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author pc
 */
@Slf4j
@RestController
@RequestMapping("order")
public class OrderController {

    public static final String PAYMENT_URL = "http://cloud-provider-payment-consul/cloud-provider-payment-consul";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("discoveryConsul")
    public String discoveryConsul() {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/consul", String.class);
    }

}
