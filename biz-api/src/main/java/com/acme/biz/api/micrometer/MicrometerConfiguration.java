package com.acme.biz.api.micrometer;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.cloud.client.serviceregistry.Registration;

import static java.util.Arrays.asList;

/**
 * Micrometer 通用配置
 * @author <a href="kingyo7781@gmail.com">WuHao</a>
 * @since 1.0.0
 */
public class MicrometerConfiguration implements MeterRegistryCustomizer {


    @Autowired
    private Registration registration;


    @Value("${spring.application.name:default}")
    private String applicationName;

    @Override
    public void customize(MeterRegistry registry) {
        registry.config().commonTags(asList(
                Tag.of("applicationName",applicationName),  //　应用维度的tag
                Tag.of("host",registration.getHost())       //　实例维度的tag
        ));
    }
}
