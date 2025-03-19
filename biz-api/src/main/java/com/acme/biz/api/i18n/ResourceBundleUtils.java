package com.acme.biz.api.i18n;

import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * @author: wuhao
 * @time: 2025/3/15 21:01
 */
public class ResourceBundleUtils extends ResourceBundle {

    private static final Object[][] contents = new Object[][]{{"MyName", "WUHAO"}};

    public Object[][] getContents() {
        return contents;
    }

    @Override
    protected Object handleGetObject(String key) {
        return null;
    }

    @Override
    public Enumeration<String> getKeys() {
        return null;
    }
}
