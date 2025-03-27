package com.acme.biz.web.servlet.embedded.tomcat;

import org.apache.coyote.AbstractProtocol;
import org.apache.coyote.ProtocolHandler;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;

/**
 *  自定义tomcat 实现 {@link ProtocolHandler}
 * @author: wuhao
 * @since 1.0.0
 */
public class MyTomcatProtocolHandleCustomizer implements TomcatProtocolHandlerCustomizer {
    private TomcatServletWebServerFactory factory;

    public MyTomcatProtocolHandleCustomizer(TomcatServletWebServerFactory factory) {
        this.factory = factory;
    }

    @Override
    public void customize(ProtocolHandler protocolHandler) {
        if(protocolHandler instanceof AbstractProtocol){
            AbstractProtocol protocol = (AbstractProtocol) protocolHandler;
            protocol.setMaxThreads(100);

        }

    }
}
