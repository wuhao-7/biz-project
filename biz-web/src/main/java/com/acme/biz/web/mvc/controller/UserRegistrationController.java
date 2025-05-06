package com.acme.biz.web.mvc.controller;

import com.acme.biz.api.interfaces.UserRegistrationService;
import com.acme.biz.api.model.User;
import com.acme.biz.web.service.InMemoryUserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: wuhao
 * @time: 2025/3/8 15:44
 */
@RestController
public class UserRegistrationController implements UserRegistrationService {

    @Autowired
    public InMemoryUserRegistrationService userRegistrationService;
    @Override
    @ResponseBody
    public Boolean registerUser(User user) {return userRegistrationService.registerUser(user);}
}
