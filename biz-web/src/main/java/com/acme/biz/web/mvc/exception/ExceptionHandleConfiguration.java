package com.acme.biz.web.mvc.exception;

import com.acme.biz.api.ApiResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;

/**
 * @author: wuhao
 * @time: 2025/3/6 16:34
 */
@RestControllerAdvice
public class ExceptionHandleConfiguration {
    @ExceptionHandler(ValidationException.class)
    public ApiResponse<Void> onValidationException(ValidationException e){
        ApiResponse response = ApiResponse.failed(null,e.getMessage());
        return  response;
    }

}
