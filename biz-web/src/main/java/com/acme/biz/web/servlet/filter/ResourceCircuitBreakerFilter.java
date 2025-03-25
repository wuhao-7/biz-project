package com.acme.biz.web.servlet.filter;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.control.Try;
import org.springframework.util.ClassUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** {@link CircuitBreaker}
 * filter 实现web 容错 限流  细粒度
 *
 * @author: wuhao
 * @time: 2025/3/19 21:12
 */
@WebFilter(filterName = "resourceCircuitBreakerFilter",urlPatterns = "/*",dispatcherTypes = {
             DispatcherType.REQUEST,
                DispatcherType.FORWARD
})
public class ResourceCircuitBreakerFilter implements Filter {

    private  CircuitBreakerRegistry circuitBreakerRegistry;

    private Map<String,CircuitBreaker> circuitBreakerCache;

    private static final String FILTER_CHAIN_IMPL_CLASS_NAME  = "org.apache.catalina.core.ApplicationFilterChain";
    private static final Class<?> FILTER_CHAIN_IMPL_CLASS = ClassUtils.resolveClassName(FILTER_CHAIN_IMPL_CLASS_NAME,null);// 通过类名解析类的实例 null 表示使用当前context 线程的classloader
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig
                .custom()
                .build();
        // create CircuitBreakerRegistry with a custom default CircuitBreaker configuration.
        this.circuitBreakerRegistry = CircuitBreakerRegistry.of(circuitBreakerConfig);
        circuitBreakerCache = new ConcurrentHashMap<>();

    }

    /**
     *参考实现{@link CircuitBreaker#decorateCheckedSupplier}
     *
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String  requestURI = request.getRequestURI();
        String servletName = getServletTypeName(request,filterChain);
        if(servletName == null){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        //servlet 未提供api 来解决HTTP 请求来匹配映射到那个 servlet 使用 requestURI 会打爆内存
        CircuitBreaker circuitBreaker = circuitBreakerCache.computeIfAbsent(servletName,circuitBreakerRegistry::circuitBreaker);
        Try.run(circuitBreaker.decorateCheckedRunnable(()->filterChain.doFilter(servletRequest,servletResponse)));
    }

    private String getServletTypeName(ServletRequest request,FilterChain chain) throws ServletException {
        Servlet servlet = null;
        try {
            if(FILTER_CHAIN_IMPL_CLASS != null){
                Field field = FILTER_CHAIN_IMPL_CLASS.getDeclaredField("servlet");
                field.setAccessible(true);
                servlet = (Servlet) field.get(chain);
                if(servlet != null){
                    return  servlet.getServletConfig().getServletName();
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
           throw new ServletException();
        }
        return  null;
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
