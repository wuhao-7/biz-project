package com.acme.biz.web.mvc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.Set;

/**
 *  Jmx Controller
 * @author <a href="kingyo7781@gmail.com">WuHao</a>
 * @since 1.0.0
 */
@RestController
@RequestMapping("/jmx")
public class JmxController {

    private  final MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

    @GetMapping("/query/name/{name}")
    public Set<ObjectName> query(@PathVariable String name) throws MalformedObjectNameException {
        ObjectName objectName = new ObjectName(name);
        return mBeanServer.queryNames(objectName,objectName);
    }
}

