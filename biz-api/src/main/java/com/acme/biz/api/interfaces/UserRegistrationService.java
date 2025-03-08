package com.acme.biz.api.interfaces;

import com.acme.biz.api.model.User;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * 用户注册服务接口 (Open feign. Dubbo 等公用)
 * @author: wuhao
 * @time: 2025/3/4 15:40
 */
@FeignClient("${user-registration.service.name}")
@RequestMapping("/user")
@DubboService
public interface UserRegistrationService {

    @PostMapping(value = "/register" ,produces = "application/json;v=1.0")
    Boolean registerUser(@RequestBody @Validated @Valid User user);

}
