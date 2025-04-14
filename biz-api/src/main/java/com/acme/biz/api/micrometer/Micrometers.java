package com.acme.biz.api.micrometer;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <a href="kingyo7781@gmail.com">WuHao</a>
 * @since 1.0.0
 */
public class Micrometers {

    private static final ExecutorService asyncExecutor = Executors.newSingleThreadExecutor();

    public static void async(Runnable runnable){
        asyncExecutor.submit(runnable);
    }

}
