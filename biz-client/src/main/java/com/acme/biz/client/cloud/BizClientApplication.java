package com.acme.biz.client.cloud;

import com.acme.biz.api.interfaces.UserRegistrationService;
import com.acme.biz.api.model.User;
import com.acme.biz.client.cloud.loadBalancer.CpuUsageBalancerConfigruation;
import com.acme.biz.client.loadBalancer.UserServiceLoadBalancerConfigruation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author: wuhao
 * @time: 2025/3/30 0:41
 */
@EnableDiscoveryClient
@EnableAutoConfiguration
@EnableFeignClients(clients = UserRegistrationService.class)
@LoadBalancerClient(name = "user-service", configuration = CpuUsageBalancerConfigruation.class)
@EnableScheduling
public class BizClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(BizClientApplication.class, args);
    }

    @Autowired
    private UserRegistrationService userRegistrationService;

    @Scheduled(fixedRate = 10 * 1000L)
    public void registerUser(){
        User user = new User();
        user.setId(12L);
        user.setName("ww.");
        System.out.println( userRegistrationService.registerUser(user));
    }

}
