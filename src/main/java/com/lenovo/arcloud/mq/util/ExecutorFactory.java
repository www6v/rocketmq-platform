/*
 * Copyright 2009-2017 Lenovo Software, Inc. All rights reserved.
 */
package com.lenovo.arcloud.mq.util;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/29
 *
 */
public class ExecutorFactory {
    private static final ThreadFactory DEFAULT_THREAD_FACTORY = new ArThreadFactory("ArCloud-ThreadFactory", true);

    private ExecutorFactory() {
    }

    public static ThreadPoolExecutor newFixedThreadPool(int nThreads, int workQueueMaxSize,
        ThreadFactory threadFactory) {
        return new ThreadPoolExecutor(nThreads, nThreads, 0, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(workQueueMaxSize), threadFactory);
    }

    public static ThreadPoolExecutor newFixedThreadPool(int nThreads, int workQueueMaxSize) {
        return newFixedThreadPool(nThreads, workQueueMaxSize, DEFAULT_THREAD_FACTORY);
    }

    public static ThreadPoolExecutor newFixedThreadPool(int nThreads, int workQueueMaxSize, String threadFactoryName,
        boolean daemon) {
        ThreadFactory threadFactory = ArThreadFactory.createThreadFactory(threadFactoryName, daemon);
        return newFixedThreadPool(nThreads, workQueueMaxSize, threadFactory);
    }

}