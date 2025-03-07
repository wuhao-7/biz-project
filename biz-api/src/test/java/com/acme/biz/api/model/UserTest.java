package com.acme.biz.api.model;

import com.acme.biz.api.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocaleContextMessageInterpolator;

import javax.validation.*;
import javax.validation.bootstrap.GenericBootstrap;
import java.util.Set;

/**
 * @author: wuhao
 * @time: 2025/3/6 15:55
 */
public class UserTest extends BaseTest {
    @Test
    public void testValidateUser() {

        User user = new User();

        Set<ConstraintViolation<User>> constraintViolations = super.validate(user);

        constraintViolations.forEach(cv -> {System.out.println(cv.getMessage());});
        //UnexpectedTypeException <-ConstraintDeclarationException <-ValidationException

    }

}
