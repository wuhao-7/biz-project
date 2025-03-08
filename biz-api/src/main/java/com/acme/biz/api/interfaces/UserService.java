package com.acme.biz.api.interfaces;

import com.acme.biz.api.model.User;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * 用户登录服务接口 (Open feign. Dubbo 等公用)
 * @author: wuhao
 * @time: 2025/3/4 15:40
 * @Deprecated 接口不再使用,请使用 {@link UserLoginService } 或者 {@link UserRegistrationService}
 */
@FeignClient("${user.service.name}")
@RequestMapping("/user")
@DubboService
@Deprecated
public interface UserService {

    @PostMapping("/register")
    Boolean saveUser(User user);

    @PostMapping("/login")
    @Deprecated
    User login(Map<String,Object> context);
}
