package com.acme.biz.web.method.annotaion;

import com.acme.biz.api.ApiResponse;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author: wuhao
 * @time: 2025/3/8 15:18
 * @see org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor
 */
public class ApiResponseHandlerMethodReturnValueHandle implements HandlerMethodReturnValueHandler {
    MappingJackson2HttpMessageConverter converters = new MappingJackson2HttpMessageConverter();
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return (AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), ResponseBody.class) ||
                returnType.hasMethodAnnotation(ResponseBody.class))  &&
                !ApiResponse.class.equals(returnType.getParameterType());
    }
    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        mavContainer.setRequestHandled(true);
        ApiResponse inputMessage =  ApiResponse.ok(returnValue);

        HttpServletResponse response = (HttpServletResponse)webRequest.getNativeResponse();
        response.addHeader("v","3");
        //returnvalue  =  pojo
        ServletServerHttpResponse outputMessage = createOutputMessage(webRequest);
        converters.write(inputMessage, MediaType.APPLICATION_JSON,outputMessage);
    }

    protected ServletServerHttpResponse createOutputMessage(NativeWebRequest webRequest) {
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        Assert.state(response != null, "No HttpServletResponse");
        return new ServletServerHttpResponse(response);
    }
}
