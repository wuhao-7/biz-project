package com.acme.biz.web.dicovery.client.eureka;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.sun.management.OperatingSystemMXBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;
import java.lang.management.ManagementFactory;
import java.util.Map;

/**
 * @author: wuhao
 * @time: 2025/3/30 17:30
 */
@ConditionalOnClass(name = "com.netflix.discovery.DiscoveryClient")
@Configuration(proxyBeanMethods = false)
public class EurekaClientConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(EurekaClientConfiguration.class);

    private ApplicationInfoManager applicationInfoManager;

    private static final Boolean HOTSPOT_JVM = ClassUtils.isPresent("com.sun.management.OperatingSystemMXBean",null);

    @Autowired
    private EurekaClient eurekaClient;

    @PostConstruct
    public void init(){
        this.applicationInfoManager = eurekaClient.getApplicationInfoManager();

    }

    @Scheduled(fixedRate = 5000L,initialDelay = 10L)
    public void upload(){
        InstanceInfo instanceInfo = applicationInfoManager.getInfo();
        Map<String, String>  metadata = instanceInfo.getMetadata();
        metadata.put("timestamp", String.valueOf(System.currentTimeMillis()));
        metadata.put("cpu-usage", String.valueOf(getCpuUsage()));
        instanceInfo.setIsDirty();
        logger.info("Upload Eureka InstanceInfo's metadata");
    }

    private Integer getCpuUsage(){
        if (HOTSPOT_JVM) {
            OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
            Double cpuLode = operatingSystemMXBean.getSystemCpuLoad() * 100;
            return cpuLode.intValue();
        }else {
            return  0;
        }

    }

}
