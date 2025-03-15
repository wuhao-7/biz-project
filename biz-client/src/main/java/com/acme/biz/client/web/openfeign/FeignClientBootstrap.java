package com.acme.biz.client.web.openfeign;

import com.acme.biz.api.interfaces.UserRegistrationRestService;
import com.acme.biz.api.interfaces.UserRegistrationService;
import com.acme.biz.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientSpecification;
import org.springframework.cloud.openfeign.FeignContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: wuhao
 * @time: 2025/3/12 21:59
 */
@EnableFeignClients(clients = UserRegistrationService.class)
public class FeignClientBootstrap {

    @Autowired(required = false)
    private List<FeignClientSpecification> configurations = new ArrayList<>();

    @Bean
    public FeignContext feignContext() {
        FeignContext context = new FeignContext();
        context.setConfigurations(this.configurations);
        return context;
    }
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //注册当前类
        applicationContext.register(FeignClientBootstrap.class);
        //启动上下文
        applicationContext.refresh();

        UserRegistrationService userRegistrationService = applicationContext.getBean(UserRegistrationService.class);

        User user = new User();
        user.setId(12L);
        user.setName("ww.");
        userRegistrationService.registerUser(user);
        // todo
        //关闭
        applicationContext.close();



    }
}
