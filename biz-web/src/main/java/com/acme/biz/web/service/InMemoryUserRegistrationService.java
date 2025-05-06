package com.acme.biz.web.service;

import com.acme.biz.api.interfaces.UserRegistrationService;
import com.acme.biz.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.CurrentTraceContext;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 内存实现 InMemoryUserRegistrationService
 * @author <a href="kingyo7781@gmail.com">WuHao</a>
 * @since 1.0.0
 */
@Service("UserRegistrationService")
public class InMemoryUserRegistrationService implements UserRegistrationService {

    @Autowired
    private Tracer tracer;

    @Autowired
    private CurrentTraceContext currentTraceContext;

    private Map<Long, User> userCache = new ConcurrentHashMap<>();

    @Override
    public Boolean registerUser(User user) {
        Long id = user.getId();

        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        Span initialSpan = (Span)request.getAttribute(Span.class.getName());
        Span newSpan = null;
        Boolean success = false;
        try(Tracer.SpanInScope ws = this.tracer.withSpan(initialSpan)){
            newSpan = this.tracer.nextSpan().name("UserRegistrationService");
            newSpan.start();
            success = userCache.putIfAbsent(id,user) == null;
        }finally {
            if(newSpan != null){
                newSpan.end();
            }
        }
        return success;
    }
}
