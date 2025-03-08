package com.acme.biz.web.controller;

import com.acme.biz.api.interfaces.UserRegistrationService;
import com.acme.biz.api.model.User;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: wuhao
 * @time: 2025/3/8 15:44
 */
@RestController
public class UserRegistrationController implements UserRegistrationService {
    @Override
    public Boolean registerUser(User user) {return Boolean.TRUE;}
}
