package com.acme.biz.client.cloud.loadBalancer;

import com.acme.biz.client.loadBalancer.UserServiceServiceInstanceListSupplier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.RandomLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author: wuhao
 * @time: 2025/3/13 14:03
 * @see  com.acme.biz.api.interfaces.UserRegistrationService
 */
@Configuration(proxyBeanMethods = false)
public class CpuUsageBalancerConfigruation  {

    @Bean
    ReactorLoadBalancer<ServiceInstance> randomLoadBalance(Environment environment,
                                                           LoadBalancerClientFactory loadBalancerClientFactory){
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new CpuUsageLoadBalancer(loadBalancerClientFactory
                .getLazyProvider(name,ServiceInstanceListSupplier.class));
    }

}
