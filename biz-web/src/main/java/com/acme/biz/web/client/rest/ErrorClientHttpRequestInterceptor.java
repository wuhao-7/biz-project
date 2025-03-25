package com.acme.biz.web.client.rest;

import com.acme.biz.api.ApiResponse;
import org.springframework.boot.web.servlet.filter.OrderedRequestContextFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;

import javax.validation.ValidationException;
import javax.validation.Validator;
import java.io.IOException;

/**
 * 请求前处理拦截器
 * @author: wuhao
 * @time: 2025/3/10 15:21
 */
public class ErrorClientHttpRequestInterceptor implements ClientHttpRequestInterceptor , Ordered {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();
        Boolean valid = "true".equals(headers.getFirst("validation-result"));
        if(!valid){
            throw new ValidationException("valid fail!");

        }
        return execution.execute(request, body);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
