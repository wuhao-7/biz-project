package com.acme.biz.api.openFeign;

import com.acme.biz.api.ApiResponse;
import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author: wuhao
 * @time: 2025/3/13 16:32
 */

@Configuration
public class ApiResponseDecoder implements Decoder {

    private final Decoder decoder;

    public ApiResponseDecoder(Decoder decoder) {
        this.decoder = decoder;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {
        //服务端返回ApiResponse 客户端需要boolean
        //多版本控制
        String contextType = getContextType(response);
        MediaType mediaType = MediaType.parseMediaType(contextType);
        String version = mediaType.getParameter("v");
        if(version != null){
            Object object = decoder.decode(response, ApiResponse.class);
            if(object instanceof ApiResponse){
                return ((ApiResponse<?>) object).getBody();
            }
        }

        return decoder.decode(response, type);
    }

    private String getContextType(Response response) {
        Collection<String> contextType = response.headers().getOrDefault("Context-type", Arrays.asList("application/json;v=3"));
        return contextType.iterator().next();
    }
}