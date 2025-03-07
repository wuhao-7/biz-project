package com.acme.biz.api.model;


import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author: wuhao
 * @time: 2025/3/4 15:31
 */
public class User {

    @NotNull
    private  Long id;

    @NotNull
    @NotEmpty
    private  String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
