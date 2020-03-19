package com.alibaba.thread.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * 功能描述:
 * @Param: 非阻塞式队列和非阻塞式队列的demo
 * @result:
 * @author: HuaShengZeng
 * @date: 2020/3/19 22:00
 */
public class ConcurrentLinkedQueueAndBlockingQueueTest {
    public static void main(String[] args) throws InterruptedException {
//        非阻塞式队列
        ConcurrentLinkedQueue<String> concurrentLinkedQueue = new ConcurrentLinkedQueue();
        concurrentLinkedQueue.offer("aaa");
        concurrentLinkedQueue.offer("bbb");
        concurrentLinkedQueue.offer("ccc");
//         获取队列会删除
        System.out.println(concurrentLinkedQueue.poll());
//        取的时候不删除
        System.out.println(concurrentLinkedQueue.peek());
//获取队列个数
        System.out.println(concurrentLinkedQueue.size());

//        阻塞队列
        BlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<String>(3);
        //添加非租塞式
        arrayBlockingQueue.offer("aaa");
//        特别注意：当队列没满的时候，不会等待！！！！
        //添加阻塞式
        arrayBlockingQueue.offer("bbb",3, TimeUnit.SECONDS);
        arrayBlockingQueue.offer("bbb",3, TimeUnit.SECONDS);
        System.out.println(arrayBlockingQueue.offer("bbb", 3, TimeUnit.SECONDS));

    }
}
