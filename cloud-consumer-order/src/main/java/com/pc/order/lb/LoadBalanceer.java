package com.pc.order.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * 自定义负载均衡策略接口
 */
public interface LoadBalanceer {

    /**
     * 获取一个实例调用
     * @param serviceInstance
     * @return
     */
    public ServiceInstance getInstance(List<ServiceInstance> serviceInstance);

}
