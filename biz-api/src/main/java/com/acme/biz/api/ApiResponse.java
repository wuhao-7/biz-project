package com.acme.biz.api;

import com.acme.biz.api.enums.StatusCode;
import org.springframework.util.MultiValueMap;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

/**
 * @author: wuhao
 * @time: 2025/3/6 14:17
 * @param <T>
 */
public class ApiResponse <T> extends ApiBase<T> {

    private int code;

    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static <T> ApiResponse ok(T body){
        return of(body,StatusCode.OK);
    }

    public static <T> ApiResponse failed(T body){
        return of(body,StatusCode.FAILED);
    }

    public static <T> ApiResponse failed(T body, String errormessage){
        ApiResponse<T> response =  of(body,StatusCode.FAILED);
        response.setMessage(errormessage);
        return response;
    }

    public  static <T> ApiResponse<T> of(T body, StatusCode statusCode){
        ApiResponse<T> response = new ApiResponse<>();
        response.setBody(body);
        response.setCode(statusCode.getCode());
        response.setMessage(statusCode.getLocalizedMessage());
        return response;
    }


    public  static class Builder <T> {

        private int code;
        private String message;

        public Builder<T> code(int code) {
            this.code =code;
            return this;
        }
    }

}
