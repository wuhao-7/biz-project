package com.acme.biz.api.fault.tolerance;

import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.http.HttpRequest;

import java.util.Collection;
import java.util.Map;

/**
 * @author: wuhao
 * @time: 2025/3/23 20:56
 */
public class BulkHeadRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        Request request = template.request();
        Map<String, Collection<String>> headers = request.headers();

    }
}
