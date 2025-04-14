package com.acme.biz.api.feign.config;

import com.acme.biz.api.micrometer.MicrometerConfiguration;
import com.acme.biz.api.micrometer.fegin.FeignCallCounterMetrics;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * 默认实现 FeignClient
 * @author <a href="kingyo7781@gmail.com">WuHao</a>
 * @see FeignClientsConfiguration
 * @since 1.0.0
 */
@Import({FeignClientsConfiguration.class})
public class DefaultFeignClientsConfiguration {

    @Bean
    public FeignCallCounterMetrics feignCallCounterMetrics(){
        return new FeignCallCounterMetrics();
    }
}
