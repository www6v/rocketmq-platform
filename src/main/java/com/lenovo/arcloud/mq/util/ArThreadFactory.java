package com.lenovo.arcloud.mq.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/29
 *
 */
public class ArThreadFactory implements ThreadFactory{
    private final static AtomicInteger FACTORY_NUMBER = new AtomicInteger(0);
    private final static AtomicInteger threadNumber = new AtomicInteger(0);

    private final String threadPrefix;
    private final boolean daemon;

    public ArThreadFactory(){
        this("ArCloud",false);
    }
    public ArThreadFactory(String threadName){
        this(threadName,false);
    }
    public ArThreadFactory(String threadName,boolean daemon){
        if(threadName == null){
            throw new NullPointerException("threadName");
        }
        this.threadPrefix = prefix(threadName,FACTORY_NUMBER.getAndIncrement());
        this.daemon = daemon;

    }

    private String prefix(String threadName,int factoryId){
        final StringBuilder sb = new StringBuilder(32);
        sb.append(threadName);
        sb.append('-');
        sb.append(factoryId);
        sb.append('-');
        return sb.toString();
    }

    @Override
    public Thread newThread(Runnable r) {
        String newThreadName = createThreadName();
        Thread thread = new Thread(r,newThreadName);
        if(daemon){
            thread.setDaemon(true);
        }
        return thread;
    }

    private String createThreadName(){
        StringBuilder sb = new StringBuilder(threadPrefix.length()+8);
        sb.append(threadPrefix);
        sb.append(threadNumber.getAndIncrement());
        return sb.toString();
    }

    public static ThreadFactory createThreadFactory(String threadName){
        return createThreadFactory(threadName,false);
    }

    public static ThreadFactory createThreadFactory(String threadName,boolean daemon){
        return new ArThreadFactory(threadName,daemon);
    }
}
