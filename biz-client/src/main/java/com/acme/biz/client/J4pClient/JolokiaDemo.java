package com.acme.biz.client.J4pClient;



import java.util.Map;

/**
 *
 *
 * 使用jdk 11 启动
// * @see  J4pClient
 * @author: wuhao
 *
 */
public class JolokiaDemo {

//    public static void main(String[] args) throws Exception {
//        J4pClient j4pClient = J4pClient.url("http://localhost:8080/jolokia")
//                .user("jolokia")
//                .password("jolokia")
//                .build();
//        J4pReadRequest req = new J4pReadRequest("java.lang:type=Memory", "HeapMemoryUsage");
//        J4pReadResponse resp = j4pClient.execute(req);
//        Map<String, Long> vals = resp.getValue();
//        long used = vals.get("used");
//        long max = vals.get("max");
//        int usage = (int) (used * 100 / max);
//        System.out.println("Memory usage: used: " + used + " / max: " + max + " = " + usage + "%");
//    }
}
