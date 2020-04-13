package com.pc.order.controller;

import com.pc.entities.JsonResult;
import com.pc.entities.Payment;
import com.pc.order.lb.LoadBalanceer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaServiceInstance;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author pc
 */
@Slf4j
@RestController
@RequestMapping("order")
public class OrderController {

//    public static final String PAYMENT_URL = "http://localhost:8001/cloud-provider-payment";

    public static final String PAYMENT_URL = "http://CLOUD-PROVIDER-PAYMENT/cloud-provider-payment";

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private LoadBalanceer loadBalanceer;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping("create")
    public JsonResult<Payment> create(Payment payment) {
        log.info("参数：" + payment);

        //这里设置一下restTemplate的content-type类型为json，要不然我们传个实体过去就gg了，使用xml的格式发送的
        //因为内部依据我们传入的实体进行了转化
        // if (requestBody instanceof HttpEntity) {
        //                this.requestEntity = (HttpEntity)requestBody;
        //            } else if (requestBody != null) {
        //                this.requestEntity = new HttpEntity(requestBody);
        //            } else {
        //                this.requestEntity = HttpEntity.EMPTY;
        //            }
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Payment> entity = new HttpEntity<>(payment, header);
        JsonResult result = restTemplate.postForObject(PAYMENT_URL + "/payment/create", entity, JsonResult.class);

//        HttpRequest post = HttpUtil.createPost(PAYMENT_URL + "/payment/create");
//        post.body(JSONUtil.toJsonStr(payment));
//        HttpResponse execute = post.execute();
//        System.out.println(execute.body());
        log.info("结果：" + result);
        return result;
    }

    @GetMapping("/getPaymentById/{id}")
    public JsonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        JsonResult result = restTemplate.getForObject(PAYMENT_URL + "/payment/getPaymentById/" + id, JsonResult.class);
        log.info("结果：" + result);
        return result;
    }

    @GetMapping("/getPaymentById2/{id}")
    public JsonResult<Payment> getPaymentById2(@PathVariable("id") Long id) {
        ResponseEntity<JsonResult> forEntity = restTemplate.getForEntity(PAYMENT_URL + "/payment/getPaymentById/" + id, JsonResult.class);
        if (forEntity.getStatusCode().is2xxSuccessful()) {
            return forEntity.getBody();
        } else {
            return new JsonResult<>(200, "查询失败");
        }
    }

    @GetMapping("customLb")
    public String customLb() {

        //获取所有的服务名
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PROVIDER-PAYMENT");

        ServiceInstance instance = loadBalanceer.getInstance(instances);
        if (instance == null) {
            return "无服务调用";
        }

        System.out.println(instance.getUri());

        EurekaServiceInstance eurekaServiceInstance = (EurekaServiceInstance) instance;
        System.out.println(eurekaServiceInstance.getUri());
        System.out.println(eurekaServiceInstance.getInstanceInfo().getVIPAddress());

        return restTemplate.getForObject(eurekaServiceInstance.getUri() + "/" +
                eurekaServiceInstance.getInstanceInfo().getVIPAddress() + "/payment/lb", String.class);
    }

}
