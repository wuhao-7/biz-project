package com.acme.biz.api;

import com.acme.biz.api.model.User;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * @author: wuhao
 * @time: 2025/3/6 16:53
 */
public class ApiRequestTest extends BaseTest{

    @Test
    public void validateBody(){
         ApiRequest request = new ApiRequest();
        Set<ConstraintViolation<ApiRequest>> constraintViolations = super.validate(request);
        constraintViolations.forEach(cv -> {System.out.println(cv.getMessage());});
    }

}
