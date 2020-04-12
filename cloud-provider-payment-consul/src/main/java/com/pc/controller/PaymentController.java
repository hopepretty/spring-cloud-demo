package com.pc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author pc
 */
@Slf4j
@RestController
@RequestMapping("payment")
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    /**
     * 通过服务发现获取所有的信息
     * @return
     */
    @GetMapping("consul")
    public String discovery() {
        return "spring cloud with consul:" + serverPort + "\t" + UUID.randomUUID().toString();
    }

}
