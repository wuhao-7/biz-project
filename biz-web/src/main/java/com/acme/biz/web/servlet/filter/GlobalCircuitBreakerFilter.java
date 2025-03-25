package com.acme.biz.web.servlet.filter;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.CheckedRunnable;
import io.vavr.control.Try;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/** {@link CircuitBreaker}
 * filter 实现web 容错 限流
 *
 * 缺陷 粗粒度 global
 * @author: wuhao
 * @time: 2025/3/19 21:12
 */
@WebFilter(filterName = "globalCircuitBreakerFilter",urlPatterns = "/*",dispatcherTypes = {
             DispatcherType.REQUEST,
                DispatcherType.FORWARD
})
public class GlobalCircuitBreakerFilter implements Filter {

    private CircuitBreaker circuitBreaker;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig
                .custom()
                .build();
        // create CircuitBreakerRegistry with a custom default CircuitBreaker configuration.
        CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(circuitBreakerConfig);
        String filterName = filterConfig.getFilterName();
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker(filterName);

    }

    /**
     *参考实现{@link CircuitBreaker#decorateCheckedSupplier}
     *
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        circuitBreaker.acquirePermission();
        final long start = circuitBreaker.getCurrentTimestamp();
        try {
            long duration = circuitBreaker.getCurrentTimestamp() - start;
            circuitBreaker.onSuccess(duration, circuitBreaker.getTimestampUnit());
            filterChain.doFilter(servletRequest,servletResponse);
        } catch (Exception exception) {
            // Do not handle java.lang.Error
            long duration = circuitBreaker.getCurrentTimestamp() - start;
            circuitBreaker.onError(duration, circuitBreaker.getTimestampUnit(), exception);
            throw new ServletException();
        }



//        Try.run(circuitBreaker.decorateCheckedRunnable(()->filterChain.doFilter(servletRequest,servletResponse)))
//                .andThen(()-> System.out.println("111"));
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
