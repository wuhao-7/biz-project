package com.acme.biz.web.servlet.Interceptor;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: wuhao
 * @time: 2025/3/21 16:46
 */
public class ResourceBulkHeadInterceptor implements HandlerInterceptor, InitializingBean, ApplicationListener<ContextRefreshedEvent> {

    private static   BulkheadConfig config;
    private Map<Method, Bulkhead> bulkheadMethodMap;
    @Deprecated
    private Map<String, Bulkhead> bulkheadCache;

    @Override
    public void afterPropertiesSet() throws Exception {
         config = BulkheadConfig.custom()
                .build();
         bulkheadCache = new ConcurrentHashMap<>();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Bulkhead bulkhead = doGetBulkHead(handlerMethod);
            bulkhead.acquirePermission();
        }

        return true;
    }

    public Bulkhead doGetBulkHead(HandlerMethod handlerMethod){
        Method method = handlerMethod.getMethod();
        return bulkheadMethodMap.get(method);
    }

    @Deprecated
    public Bulkhead getBulkHead(HandlerMethod handlerMethod){
        String resourceName = getResourceName(handlerMethod);

        return bulkheadCache.computeIfAbsent(resourceName,n->Bulkhead.of(resourceName,config));
    }

    private String getResourceName(HandlerMethod handlerMethod) {
        AnnotationAttributes annotation =
                AnnotatedElementUtils.getMergedAnnotationAttributes(handlerMethod.getMethod(), RequestMapping.class);
        String[] path = annotation.getStringArray("path");
        return path[0];
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Bulkhead bulkhead = doGetBulkHead(handlerMethod);
            bulkhead.releasePermission();
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.bulkheadMethodMap = new HashMap<>();
        ApplicationContext context = event.getApplicationContext();
        Map<String,RequestMappingHandlerMapping> requestMapping =
                context.getBeansOfType(RequestMappingHandlerMapping.class);
        for (RequestMappingHandlerMapping requestMappingHandleMapping :requestMapping.values()) {
            Map<RequestMappingInfo, HandlerMethod> methodMap =  requestMappingHandleMapping.getHandlerMethods();
            for (Map.Entry<RequestMappingInfo,HandlerMethod> entry:methodMap.entrySet()) {
                RequestMappingInfo requestMappingInfo = entry.getKey();
                HandlerMethod handlerMethod = entry.getValue();
                Method method = handlerMethod.getMethod();
                String resourceName = requestMappingInfo.toString();
                bulkheadMethodMap.put(method,Bulkhead.of(resourceName,config));
            }
        }

    }
}
