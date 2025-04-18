package com.acme.biz.api.micrometer.binder.servo;

import io.micrometer.core.instrument.FunctionCounter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.StringUtils;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.Collections;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.ToDoubleFunction;

/**
 * @author <a href="kingyo7781@gmail.com">WuHao</a>
 * @since TODO
 */
public class ServoMetrics implements MeterBinder, ApplicationListener<ApplicationStartedEvent> {

    private final static Logger logger = LoggerFactory.getLogger(ServoMetrics.class);

    private final static String OBJECT_NAME_PATTERN = "com.netflix.servo:*";

    private static MeterRegistry meterRegistry;

    private static MBeanServer mBeanServer;

    private ConversionService conversionService;

    private  ClassLoader classLoader;

    @Override
    public void bindTo(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {// spring boot 完全启动后
       // initClassLoader(event);
        initConversionService(event);

        registryServoMetrics();
    }

    private void initConversionService(ApplicationStartedEvent event) {
        this.conversionService = event.getApplicationContext().getEnvironment().getConversionService();
    }

    @Deprecated
    private void initClassLoader(ApplicationStartedEvent event) {
        this.classLoader =  event.getApplicationContext().getClassLoader();
    }

    private void registryServoMetrics(){
        this.mBeanServer =  ManagementFactory.getPlatformMBeanServer();
        Set<ObjectName> objectNames = findServoObjectName();
        for (ObjectName objectName:objectNames) {
            //registryServoMeter(objectName);
            registryServoMetrics(objectName);
        }

    }
    private void registryServoMeter(ObjectName objectName){
        try {
            String type = objectName.getKeyProperty("type");
            MBeanInfo mBeanInfo = mBeanServer.getMBeanInfo(objectName);
            switch (type){
                case "COUNTER":
                    registryServoCounterMeter(objectName,mBeanInfo);
                    break;
                case "GAUGE":
                    //registryCounterMetrics(objectName,mBeanInfo);
                    break;
                default:
                    // todo
                    break;
            }
        }  catch (Throwable e) {
            logger.error("");
        }


    }

    @Deprecated
    private void registryServoCounterMeter(ObjectName objectName,MBeanInfo mBeanInfo) throws Throwable{
        MBeanAttributeInfo[] attributes = mBeanInfo.getAttributes();
        String name = objectName.getKeyProperty("name");
        String className = objectName.getKeyProperty("class");
        for (MBeanAttributeInfo attribute:attributes) {
            String attributeName = attribute.getName();
            String countName = buildMeterName(objectName);

            FunctionCounter.builder(countName, this, mbs ->{
                Double countValue = null;
                try {
                        Object attributeValue = mBeanServer.getAttribute(objectName,attributeName);
                        countValue = conversionService.convert(attributeValue,Double.class);
                } catch (Throwable e) {

                }
                    return  countValue;
                })
                .tags("name",name,"className",className)
                .register(meterRegistry);
        }
    }

    private String buildMeterName(ObjectName objectName){
        String type = objectName.getKeyProperty("type"); // COUNTER
        String name = objectName.getKeyProperty("name"); // DiscoveryClient-HTTPClient_Delete
        String className = objectName.getKeyProperty("class"); // NamedConnectionPool
        String id = objectName.getKeyProperty("id"); // DiscoveryClient-HTTPClient

        StringJoiner stringJoiner = new StringJoiner(".");
        appendIfPresent(stringJoiner,type)
                .appendIfPresent(stringJoiner,name)
                .appendIfPresent(stringJoiner,className)
                .appendIfPresent(stringJoiner,id);
        return stringJoiner.toString();

    }
    private ServoMetrics appendIfPresent(StringJoiner stringJoiner,String value){
        if(StringUtils.hasText(value)){
            stringJoiner.add(value);
        }
        return this;
    }


    private Set<ObjectName> findServoObjectName (){
        Set<ObjectName> objectNames = Collections.emptySet();
        try {
            ObjectName objectName = new ObjectName(OBJECT_NAME_PATTERN);
            objectNames = mBeanServer.queryNames(objectName,objectName);
        } catch (MalformedObjectNameException e) {
            throw new RuntimeException(e);
        }
        return objectNames;
    }

    private void registryServoMetrics(ObjectName objectName)  {
        MBeanInfo mBeanInfo = null;
        try {
            mBeanInfo = mBeanServer.getMBeanInfo(objectName);
            MBeanAttributeInfo[] attributes = mBeanInfo.getAttributes();

            String type = objectName.getKeyProperty("type");
            String name = objectName.getKeyProperty("name");
            String className = objectName.getKeyProperty("class");

            for (MBeanAttributeInfo attribute:attributes) {
                String attributeName = attribute.getName();
                String countName = buildMeterName(objectName);
                ToDoubleFunction toDoubleFunction =  mbs ->{
                    Double value = null;
                    try {
                        Object attributeValue = mBeanServer.getAttribute(objectName,attributeName);
                        value = conversionService.convert(attributeValue,Double.class);
                    } catch (Throwable e) {

                    }
                    return  value;
                };

                switch (type){
                    case "COUNTER":
                        FunctionCounter.builder(countName, this,toDoubleFunction)
                                .tags("name",name,"className",className)
                                .register(meterRegistry);
                        break;
                    case "GAUGE":
                        Gauge.builder(countName, this,toDoubleFunction)
                                .tags("name",name,"className",className)
                                .register(meterRegistry);
                        break;
                    default:
                        // todo
                        break;
                }
            }
        } catch (Throwable e) {
           logger.error(e.getMessage(),e);
        }

    }
}
