package com.acme.data.fault.tolerance.mybatis;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;

/**
 *  Executor 静态拦截 {@link io.github.resilience4j.circuitbreaker.CircuitBreaker} 实现
 *
 * @author: wuhao
 * @time: 2025/3/24 23:41
 */
public class CircuitBreakerExecutorDecorator extends ExecutorDecorator{

    public CircuitBreakerExecutorDecorator() {
    }

    @Override
    protected void before(MappedStatement ms) {
        getResourceName(ms);
    }

    @Override
    protected void after(MappedStatement ms) {
        getResourceName(ms);
    }

    private String getResourceName(MappedStatement ms){
        return ms.getId();
    }
}
