package com.alibaba.thread.currentlinkedqueue;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 功能描述:
 * @Param: 非阻塞式队列
 * @result:
 * @author: HuaShengZeng
 * @date: 2020/3/19 22:00
 */
public class ConcurrentLinkedQueueTest {
    public static void main(String[] args) {
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
    }
}
