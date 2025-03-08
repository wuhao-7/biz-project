package com.acme.biz.api;

import org.springframework.util.MultiValueMap;

import java.util.Map;

/**
 * @author: wuhao
 * @time: 2025/3/6 14:21
 */
public abstract class ApiBase<T> {
    private T body;
    @Deprecated
    private Map<String, String> headers;
    @Deprecated
    private MultiValueMap<String, String> metadata;

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public MultiValueMap<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(MultiValueMap<String, String> metadata) {
        this.metadata = metadata;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
