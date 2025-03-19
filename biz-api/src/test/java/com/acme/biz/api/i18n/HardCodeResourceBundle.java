package com.acme.biz.api.i18n;

import java.util.Enumeration;
import java.util.ListResourceBundle;
import java.util.ResourceBundle;

/**
 * @author: wuhao
 * @time: 2025/3/15 21:01
 */
public class HardCodeResourceBundle extends ListResourceBundle {

    private static final Object[][] contents = new Object[][]{{"myName", "WUHAO"}};

    public Object[][] getContents() {
        return contents;
    }

}
