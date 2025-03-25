package com.acme.biz.web.servlet.Interceptor;

import io.github.resilience4j.bulkhead.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;

/**
 * @author: wuhao
 * @time: 2025/3/21 16:46
 */
public class GlobalBulkHeadInterceptor implements HandlerInterceptor, InitializingBean {

    private static  Bulkhead bulkhead;

    @Override
    public void afterPropertiesSet() throws Exception {
        BulkheadConfig config = BulkheadConfig.custom()
                .build();
        bulkhead = Bulkhead.of("globalBulkHeadInterceptor",config);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        bulkhead.acquirePermission();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        bulkhead.releasePermission();
    }


}
