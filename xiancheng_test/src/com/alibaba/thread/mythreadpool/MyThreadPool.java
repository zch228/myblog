package com.alibaba.thread.mythreadpool;

import java.util.concurrent.*;

public class MyThreadPool {
    public static void main(String[] args) {
//        主要参数：核心线程数1、最大线程数2、队列长度3
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(3));
//        执行时：任务1现在已经在执行了，任务2在队列，任务3在队列
        threadPoolExecutor.execute(new TaskThread("任务1"));
        threadPoolExecutor.execute(new TaskThread("任务2"));
        threadPoolExecutor.execute(new TaskThread("任务3"));
        threadPoolExecutor.execute(new TaskThread("任务4"));
//        此时队列已满，但是核心线程数为2，所以会重新创建线程
        threadPoolExecutor.execute(new TaskThread("任务5"));
//        此时会报错，队列此时已经满了，并且最大线程数为2，此时会触发拒绝策略,抛出异常
        threadPoolExecutor.execute(new TaskThread("任务6"));
    }
}
class TaskThread implements Runnable{
    private String name;
    public TaskThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+name);
    }
}