package com.acme.biz.client.web.openfeign;

import com.acme.biz.api.interfaces.UserRegistrationService;
import com.acme.biz.api.model.User;
import com.acme.biz.client.loadBalancer.UserServiceLoadBalancerConfigruation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientSpecification;
import org.springframework.cloud.openfeign.FeignContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableLoadTimeWeaving;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: wuhao
 * @time: 2025/3/12 21:59
 */
@EnableAutoConfiguration
@EnableFeignClients(clients = UserRegistrationService.class)
@LoadBalancerClient(name = "user-service", configuration = UserServiceLoadBalancerConfigruation.class)
public class FeignClientSpringBootBootstrap {
    public static void main(String[] args) {

        ConfigurableApplicationContext context = new SpringApplicationBuilder(FeignClientSpringBootBootstrap.class)
                .web(WebApplicationType.NONE)
                .build()
                .run(args);
        UserRegistrationService userRegistrationService = context.getBean(UserRegistrationService.class);

        User user = new User();
        user.setId(12L);
        user.setName("ww.");
        System.out.println( userRegistrationService.registerUser(user));
        // todo
        //关闭
        context.close();

    }
}
