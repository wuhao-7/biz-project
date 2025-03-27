package com.acme.biz.web.servlet.embedded.tomcat;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

/**
 * 自定义 Tomcat {@link WebServerFactoryCustomizer}
 * @author: wuhao
 * @since 1.0.0
 */
// 配置BeanDefinition 元信息
@Component
public class MyTomcatWebServerFactoryCustomizer implements  WebServerFactoryCustomizer<TomcatServletWebServerFactory>{
    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        factory.addProtocolHandlerCustomizers(new MyTomcatProtocolHandleCustomizer(factory));
    }
}
