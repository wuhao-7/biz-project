package com.acme.biz.web.mvc.controller;

import com.acme.biz.api.ApiRequest;
import com.acme.biz.api.ApiResponse;
import com.acme.biz.api.interfaces.UserRegistrationRestService;
import com.acme.biz.api.model.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: wuhao
 * @time: 2025/3/6 17:24
 */
@RestController
public class UserRegistrationRestController implements UserRegistrationRestService {
    @Override
    public ApiResponse<Boolean> registerUser(User user) {
        return ApiResponse.ok(Boolean.TRUE);
    }

    @Override
    public ApiResponse<Boolean> registerUser(@Validated ApiRequest<User> userRequest) {
        return ApiResponse.ok(Boolean.TRUE);
    }
}
