package com.acme.biz.api;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.validation.beanvalidation.LocaleContextMessageInterpolator;

import javax.validation.*;
import javax.validation.bootstrap.GenericBootstrap;
import java.util.Set;

/**
 * 公用test
 * @author: wuhao
 * @time: 2025/3/6 16:56
 */
public abstract  class BaseTest {

    private  Validator validator;
    @BeforeEach
    public void init(){
        GenericBootstrap bootstrap = Validation.byDefaultProvider();
        Configuration configuration = bootstrap.configure();

        MessageInterpolator targetInterpolator = configuration.getDefaultMessageInterpolator();
        configuration.messageInterpolator(new LocaleContextMessageInterpolator(targetInterpolator));

        ValidatorFactory validatorFactory = configuration.buildValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }

    protected <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups){
        return validator.validate(object,groups);
    }

}
