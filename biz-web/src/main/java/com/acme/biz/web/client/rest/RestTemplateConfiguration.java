package com.acme.biz.web.client.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.InterceptingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.validation.Validator;
import java.util.Arrays;
import java.util.List;

/**
 * @author: wuhao
 * @time: 2025/3/9 21:34
 */
@Configuration(proxyBeanMethods = false)
@Import({ErrorClientHttpRequestInterceptor.class})
public class RestTemplateConfiguration {

    @Bean
    public ClientHttpRequestInterceptor validatingClientHttpRequestInterceptor(Validator validator){
        return new ValidatingClientHttpRequestInterceptor(validator,mappingJackson2HttpMessageConverter());
    }

    @Bean
    public RestTemplate restTemplate(List<ClientHttpRequestInterceptor> interceptors){
        List<HttpMessageConverter<?>> converters = Arrays.asList(mappingJackson2HttpMessageConverter());
        //ClientHttpRequestInterceptor 排序
        AnnotationAwareOrderComparator.sort(interceptors);
        RestTemplate restTemplate = new RestTemplate(converters);
        ClientHttpRequestFactory requestFactory =buildClientRequestFactory(interceptors);
        restTemplate.setRequestFactory(requestFactory);
        //TODO 增加ResponseErrorHandler
        return restTemplate;
    }

    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON));
        return mappingJackson2HttpMessageConverter;
    }

    private ClientHttpRequestFactory buildClientRequestFactory(List<ClientHttpRequestInterceptor> interceptors) {
        //TODO 替换SimpleClientHttpRequestFactory
        ClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        return new InterceptingClientHttpRequestFactory(clientHttpRequestFactory,interceptors);
    }


}
