package com.acme.biz.api.i18n;

import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;

/**
 * @author: wuhao
 * @time: 2025/3/15 21:02
 */
public class ResourceBundleUtilsTest {

    @Test
    public void testJavaProperties(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("META-INF/Messages");
        System.out.println(resourceBundle.getString("myName"));
    }

    @Test
    public void testJavaClass(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("com.acme.biz.api.i18n.HardCodeResourceBundle");
        System.out.println(resourceBundle.getString("myName"));
    }
}
