package com.acme.biz.api.interfaces;

import com.acme.biz.api.model.User;
import com.acme.biz.api.openFeign.UserServiceFeignClientConfiguration;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * 用户注册服务接口 (Open feign. Dubbo 等公用)
 * @author: wuhao
 * @time: 2025/3/4 15:40
 */
@FeignClient(name = "user-service",configuration = UserServiceFeignClientConfiguration.class)
@DubboService
public interface UserRegistrationService {

    @PostMapping(value = "/user/register" ,produces = "application/json;v=3")
    Boolean registerUser(@RequestBody @Validated @Valid User user);

}
