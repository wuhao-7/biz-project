package com.acme.biz.web.client.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * @author: wuhao
 * @time: 2025/3/10 15:53
 */
public class RetryClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)  {
        ClientHttpResponse response= null;
        try{
            response = execution.execute(request,body);
            if(!response.getStatusCode().is2xxSuccessful()){
                //retry
            }
        } catch (IOException e) {
            //retry
        }
        return response;
    }
}
