package com.acme.biz.api.interfaces;

import com.acme.biz.api.ApiBase;
import com.acme.biz.api.ApiRequest;
import com.acme.biz.api.ApiResponse;
import com.acme.biz.api.model.User;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * 用户注册服务 REST 接口 (Open feign. Spring MVC 等公用)
 * @author: wuhao
 * @time: 2025/3/4 15:40
 */
@FeignClient("${user-registration.rest.service.name}")
@RequestMapping("/api/user")
@DubboService
public interface UserRegistrationRestService {

    @PostMapping("/register/v1")
    ApiResponse<Boolean> registerUser(@RequestBody @Validated @Valid User user);

    @PostMapping("/register/v2")
    ApiResponse<Boolean> registerUser(@RequestBody @Validated @Valid ApiRequest<User> userApiRequest);

}
