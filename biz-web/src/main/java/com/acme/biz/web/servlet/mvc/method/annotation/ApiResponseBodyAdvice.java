package com.acme.biz.web.servlet.mvc.method.annotation;

import com.acme.biz.api.ApiResponse;
import com.acme.biz.api.model.User;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author: wuhao
 * @time: 2025/3/16 15:09
 */
public class ApiResponseBodyAdvice implements ResponseBodyAdvice<User> {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return converterType.isInstance(ApiResponse.class);
    }


    @Override
    public User beforeBodyWrite(User body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        return null;
    }
}
