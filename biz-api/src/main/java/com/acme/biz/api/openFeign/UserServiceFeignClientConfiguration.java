package com.acme.biz.api.openFeign;

import feign.codec.Decoder;
import feign.optionals.OptionalDecoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.HttpMessageConverterCustomizer;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author: wuhao
 * @time: 2025/3/13 16:22
 */
@Configuration(proxyBeanMethods = false)
public class UserServiceFeignClientConfiguration {

    /**
     *创建于Spring boot
     *{@link org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration #messageConverters()}
     */
    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

    @Bean
    @ConditionalOnMissingBean
    public Decoder feignDecoder(ObjectProvider<HttpMessageConverterCustomizer> customizers) {
        return new ApiResponseDecoder(new ResponseEntityDecoder(new SpringDecoder(messageConverters, customizers)));
    }

}
