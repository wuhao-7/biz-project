package com.acme.biz.web.client.rest;

import com.acme.biz.api.ApiResponse;
import com.acme.biz.api.model.User;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonInputMessage;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Bean Validation 校验 实现{@link ClientHttpRequestInterceptor}
 * @author: wuhao
 * @time: 2025/3/9 20:35
 */
public class ValidatingClientHttpRequestInterceptor implements ClientHttpRequestInterceptor, Ordered {

    private final Validator validator;
    private final HttpMessageConverter<?>[] converters;

    public ValidatingClientHttpRequestInterceptor(Validator validator, HttpMessageConverter... httpMessageConverters) {
        this.validator = validator;
        this.converters = httpMessageConverters;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        ClientHttpResponse response= null;
        //前置处理
        Boolean valid = beforeExecute(request,body);
        HttpHeaders headers = request.getHeaders();
        headers.set("validation-result",Boolean.toString(valid));
        //请求处理(next interceptor)
        response = execution.execute(request,body);
        //后置处理
        return afterExecute(response);
    }

    private ClientHttpResponse handleError(HttpRequest request, byte[] body) {
        return null;
    }

    private boolean beforeExecute(HttpRequest request, byte[] body) {
       return validatorBean(request,body);
    }

    private boolean validatorBean(HttpRequest request, byte[] body) {
        //FastJson auto-type
        Class<?> bodyClass = resolveBodyClass(request.getHeaders());
        if(bodyClass != null){
            HttpInputMessage httpInputMessage = new MappingJacksonInputMessage(new ByteArrayInputStream(body),request.getHeaders());
            MediaType mediaType = resolveMediaType(request.getHeaders());
            for (HttpMessageConverter converter:converters) {
                if(converter.canRead(bodyClass,mediaType)){
                    try{
                        Object bean = converter.read(bodyClass,httpInputMessage);
                        Set<ConstraintViolation<Object>> validations =  validator.validate(bean);
                        if(!validations.isEmpty()){
                          return false;
                        }

                    } catch (IOException e) {
                        throw new ValidationException(e.getMessage());
                    }
                }
            }
        }
        return true;
    }

    private MediaType resolveMediaType(HttpHeaders headers) {
        return headers.getContentType();
    }

    private Class<?> resolveBodyClass(HttpHeaders headers) {
        List<String> classes =headers.remove("body-class");
        if(!ObjectUtils.isEmpty(classes)){
            String bodyClassName = classes.get(0);
            if(StringUtils.hasText(bodyClassName)){
                return ClassUtils.resolveClassName(bodyClassName,null);
            }
        }
        return User.class;
    }

    private ClientHttpResponse afterExecute(ClientHttpResponse clientHttpResponse) {
        // todo
        return clientHttpResponse;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
