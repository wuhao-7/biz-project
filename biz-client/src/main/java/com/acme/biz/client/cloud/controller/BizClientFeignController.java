package com.acme.biz.client.cloud.controller;

import com.acme.biz.api.interfaces.UserRegistrationService;
import com.acme.biz.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: wuhao
 * @time: 2025/3/30 21:41
 */
@RestController
public class BizClientFeignController {

    @Autowired
    private UserRegistrationService userRegistrationService;

  @GetMapping("/user/register")
    public Object registerUser(){
      User user = new User();
      user.setId(12L);
      user.setName("ww.");
      return userRegistrationService.registerUser(user);
  }
}
