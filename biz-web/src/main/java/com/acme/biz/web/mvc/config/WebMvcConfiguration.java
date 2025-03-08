package com.acme.biz.web.mvc.config;

import com.acme.biz.web.method.annotaion.ApiResponseHandlerMethodReturnValueHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 通过扁平化的pojo的模式能够实现接口和MVC 的统一
 * 实现在不影响原有接口返回逻辑的前提下 使用 spring-mvc:HandelMethodReturnValue 对返回值进行包装处理。可以实现REST 与doubbo 对接
 * @author: wuhao
 * @time: 2025/3/8 21:40
 */
@Configuration
public class WebMvcConfiguration {

    @Autowired
    public void restRequestMappingHandleAdapter(RequestMappingHandlerAdapter requestMappingHandlerAdapter){
        List<HandlerMethodReturnValueHandler> oldReturnValueHandlers =  requestMappingHandlerAdapter.getReturnValueHandlers();
        List<HandlerMethodReturnValueHandler> newReturnValueHandlers = new ArrayList<>(oldReturnValueHandlers);
        newReturnValueHandlers.add(0,new ApiResponseHandlerMethodReturnValueHandle());
        requestMappingHandlerAdapter.setReturnValueHandlers(newReturnValueHandlers);
    }
}
