package com.acme.biz.client.cloud;

import com.acme.biz.api.interfaces.UserRegistrationService;
import com.acme.biz.api.micrometer.MicrometerConfiguration;
import com.acme.biz.api.micrometer.fegin.FeignCallCounterMetrics;
import com.acme.biz.client.cloud.controller.BizClientFeignController;
import com.acme.biz.client.cloud.loadBalancer.CpuUsageBalancerConfigruation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author: wuhao
 * @time: 2025/3/30 0:41
 */
@ComponentScan
@EnableDiscoveryClient
@EnableAutoConfiguration
@EnableFeignClients(clients = UserRegistrationService.class)
@LoadBalancerClient(name = "user-service", configuration = CpuUsageBalancerConfigruation.class)
@EnableScheduling
@Import({FeignCallCounterMetrics.class, MicrometerConfiguration.class})
public class BizClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(BizClientApplication.class, args);
    }

    @Autowired
    private BizClientFeignController bizClientController;

    @Scheduled(fixedRate = 10 * 1000L)
    public void registerUser(){
        bizClientController.registerUser();
    }

}
