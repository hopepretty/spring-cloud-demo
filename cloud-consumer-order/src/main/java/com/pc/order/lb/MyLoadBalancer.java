package com.pc.order.lb;

import cn.hutool.core.collection.CollectionUtil;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 根据请求数寻找实例下标
 *
 * 使用自定义的负载均衡需要将 @LoadBalance注解去掉
 * @author pc
 */
@Component
public class MyLoadBalancer implements LoadBalanceer {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    /**
     * 获取新增数
     * @return
     */
    public int getAndIncr() {
        int current = 0;
        int next = 0;

        do {
            current = atomicInteger.get();
            next = current >= Integer.MAX_VALUE ? 0: current + 1;
        } while (!atomicInteger.compareAndSet(current, next));
        System.out.println("第" + next + "次访问");
        return next;
    }

    /**
     * 获取一个实例调用
     *
     * @param serviceInstance
     * @return
     */
    @Override
    public ServiceInstance getInstance(List<ServiceInstance> serviceInstance) {
        if (CollectionUtil.isEmpty(serviceInstance)) {
            return null;
        }
        int index = getAndIncr() % serviceInstance.size();
        return serviceInstance.get(index);
    }
}
