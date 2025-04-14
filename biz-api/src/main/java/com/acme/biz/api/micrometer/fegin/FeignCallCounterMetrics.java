package com.acme.biz.api.micrometer.fegin;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.cloud.client.serviceregistry.Registration;

import static com.acme.biz.api.micrometer.Micrometers.async;

/**
 * Feign 的调用计数 Metrics
 * @author: wuhao
 * @time: 2025/4/13 16:59
 */
public class FeignCallCounterMetrics implements RequestInterceptor, MeterBinder {

    private static MeterRegistry meterRegistry;
    private static Counter totalCounter;

    @Value("${spring.application.name:default}")
    private String applicationName;

    @Autowired
    private Registration registration;


    @Override
    public void apply(RequestTemplate template) { // feign 子上下文调用
        //异步执行
        async(()->{
            // 独立方法统计
            String MethodName = template.methodMetadata().configKey();
            Counter counter = Counter.builder("feign.calls")
                    .tag("applicationName",applicationName)//　应用维度的tag
                    .tag("host",registration.getHost())//　实例维度的tag
                    .tags("method",MethodName) //调用方法维度
                    .register(meterRegistry);
            counter.increment();
            //全局统计
            totalCounter.increment();
        });
    }
    @Override
    public void bindTo(MeterRegistry registry) {
        this.meterRegistry = registry;
        this.totalCounter = Counter.builder("feign.total-calls")
                .register(registry);

    }
}
