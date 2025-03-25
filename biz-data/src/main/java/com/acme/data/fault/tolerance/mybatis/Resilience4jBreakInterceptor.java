package com.acme.data.fault.tolerance.mybatis;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;

import java.util.List;
import java.util.Properties;

/**
 * 基于mybatis  拓展实现断路器 Resilience4j
 * @see org.apache.ibatis.plugin.Interceptor
 * @see io.github.resilience4j.circuitbreaker.CircuitBreaker
 *
 * @author: wuhao
 * @time: 2025/3/24 21:29
 */
public class Resilience4jBreakInterceptor implements Interceptor {

    private  List<ExecutorDecorator> decorators;
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if(target instanceof Executor){
            return DelegateExecutor((Executor)target);
        }
        return Interceptor.super.plugin(target);
    }

    private Executor DelegateExecutor(Executor target) {
        return new ExecutorDecorators(target,decorators);
    }

    public void setDecorators(List<ExecutorDecorator> decorators){
        this.decorators = decorators;
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }
}
