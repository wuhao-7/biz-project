package com.acme.biz.web;
import com.acme.biz.web.servlet.Interceptor.ResourceBulkHeadInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author: wuhao
 * @time: 2025/3/4 15:01
 */

@ServletComponentScan
@Import(ResourceBulkHeadInterceptor.class)
@EnableDiscoveryClient
@SpringBootApplication
@EnableScheduling
public class BizWebApplication implements WebMvcConfigurer {

    @Autowired
    private  List<HandlerInterceptor> interceptors;
    public static void main(String[] args) {
        SpringApplication.run(BizWebApplication.class, args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        interceptors.forEach(registry::addInterceptor);
    }
}

