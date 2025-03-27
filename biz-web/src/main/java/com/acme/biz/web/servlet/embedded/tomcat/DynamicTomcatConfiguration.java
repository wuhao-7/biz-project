package com.acme.biz.web.servlet.embedded.tomcat;

import org.apache.coyote.AbstractProtocol;
import org.apache.coyote.ProtocolHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.context.properties.ConfigurationPropertiesRebinder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.ConfigurableEnvironment;

import javax.annotation.PostConstruct;


/**
 *
 * 动态tomcat 实现
 * @author: wuhao
 * @see ConfigurationPropertiesRebinder
 * @see ConfigurationPropertiesBinder
 */
@Configuration
public class DynamicTomcatConfiguration implements TomcatProtocolHandlerCustomizer  {

    private AbstractProtocol protocol;

    private ServerProperties originalServerProperties;

    @Autowired
    private ServerProperties serverProperties;

    @Autowired
    private ConfigurableEnvironment environment;

    private Binder binder;

    @PostConstruct
    @Deprecated
    public void init()  {
        originalServerProperties = new ServerProperties();
        BeanUtils.copyProperties(serverProperties,originalServerProperties);

    }

    @PostConstruct
    public void init2()  {
        Iterable<ConfigurationPropertySource>  configurationPropertySources = ConfigurationPropertySources.get(environment);
        binder = new Binder(configurationPropertySources);
        bindOriginalServerProperties();
    }
    @Deprecated
    private void buildOriginalServerProperties (){
        ServerProperties newServerProperties = new ServerProperties();
        BeanUtils.copyProperties(serverProperties,newServerProperties);
        this.originalServerProperties = newServerProperties;
    }

    private void bindOriginalServerProperties (){
        BindResult<ServerProperties>  result =  binder.bind("server",ServerProperties.class);
        this.originalServerProperties = result.get();
    }

    @EventListener(EnvironmentChangeEvent.class)
    @Deprecated // 需要实时更新
    public void OnEnvironmentChangeEvent (EnvironmentChangeEvent event){
        ServerProperties.Tomcat.Threads originalThread =  originalServerProperties.getTomcat().getThreads();
        ServerProperties.Tomcat.Threads thread =  serverProperties.getTomcat().getThreads();
        if (originalThread.getMinSpare() != thread.getMinSpare()){
            setMaxThreads();
            bindOriginalServerProperties();
        }
    }

//    @EventListener(EnvironmentChangeEvent.class)
    @Deprecated // 无法确保是最后执行
    public void updateOriginalServerProperties (EnvironmentChangeEvent event){
        buildOriginalServerProperties();
    }

    private void setMaxThreads(){
        protocol.setMaxThreads(serverProperties.getTomcat().getThreads().getMax());
    }
    @Override
    public void customize(ProtocolHandler protocolHandler) {
        if(protocolHandler instanceof  AbstractProtocol){
            this.protocol = (AbstractProtocol) protocolHandler;
        }

    }


}
