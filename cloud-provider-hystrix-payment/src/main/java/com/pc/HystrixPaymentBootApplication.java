package com.pc;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

/**
 * @author pc
 */
@EnableCircuitBreaker
@EnableEurekaClient
@SpringBootApplication
public class HystrixPaymentBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixPaymentBootApplication.class, args);
    }

    /**
     * 此配置是为了服务监控而配置。与服务容错本身无关，springcloud升级后的坑
     * 需要配置一下servlet的urlMapping
     * @return
     */
    @Bean
    public ServletRegistrationBean getServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new HystrixMetricsStreamServlet());
        servletRegistrationBean.setLoadOnStartup(1);
        servletRegistrationBean.addUrlMappings("/hystrix.stream");
        servletRegistrationBean.setName("HystrixMetricsStreamServlet");
        return servletRegistrationBean;
    }
}
