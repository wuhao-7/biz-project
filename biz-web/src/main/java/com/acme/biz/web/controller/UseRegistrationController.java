package com.acme.biz.web.controller;

import com.acme.biz.api.ApiBase;
import com.acme.biz.api.ApiRequest;
import com.acme.biz.api.ApiResponse;
import com.acme.biz.api.interfaces.UseRegistrationRestService;
import com.acme.biz.api.model.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: wuhao
 * @time: 2025/3/6 17:24
 */
@RestController
public class UseRegistrationController implements UseRegistrationRestService {
    @Override
    public ApiResponse<Boolean> registerUser(@Validated  User user) {
        return ApiResponse.ok(Boolean.TRUE);
    }

    @Override
    public ApiResponse<Boolean> registerUser(@Validated ApiRequest<User> userRequest) {
        return ApiResponse.ok(Boolean.TRUE);
    }
}
