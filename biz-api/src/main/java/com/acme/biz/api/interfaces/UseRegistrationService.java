package com.acme.biz.api.interfaces;

import com.acme.biz.api.model.User;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户注册服务接口 (Open feign. Dubbo 等公用)
 * @author: wuhao
 * @time: 2025/3/4 15:40
 */
@FeignClient("${user-registration.service.name}")
@RequestMapping("/user")
@DubboService
public interface UseRegistrationService {

    @PostMapping("/register")
    Boolean saveUser(User user);

}
