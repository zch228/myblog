package com.alibaba.thread.queue.comsumAndproducer;

import org.springframework.util.StringUtils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产者
 */
class ProducerThread implements Runnable {

    public BlockingQueue<String> blockingQueue;
    private volatile boolean flag = true;
    AtomicInteger atomicInteger = new AtomicInteger();

    public ProducerThread(BlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        System.out.println("生产者已经启动");
        try {
            while (flag) {
                String i = atomicInteger.incrementAndGet() + "";

                boolean offer = blockingQueue.offer(i, 2, TimeUnit.SECONDS);
                if (!offer) {
                    System.out.println("生产者存入失败 i=" + i);
                }
                System.out.println("生产者存入成功 i=" + i);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("生产者结束");
        }
    }

    public void stop() {
        flag = false;
    }
}

/**
 * 消费者
 */
class ConsumerThread implements Runnable {
    public BlockingQueue<String> blockingQueue;
    private volatile boolean flag = true;
    AtomicInteger atomicInteger = new AtomicInteger();

    public ConsumerThread(BlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        System.out.println("消费者工程已启动");
        try {
            while (flag) {
                String data = blockingQueue.poll(3, TimeUnit.SECONDS);
                if (data == null) {
                    System.out.println("超过了三秒时间，没有获取到信息");
                    flag = false;
                    return;
                }
                System.out.println("消费者获取到了消息，data:" + data);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("消费者已经停止");
        }
    }
}

public class ProducerAndConsum {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(10);
        ProducerThread producerThread = new ProducerThread(blockingQueue);
        ConsumerThread consumerThread = new ConsumerThread(blockingQueue);
        Thread t1 = new Thread(producerThread);
        Thread t2 = new Thread(consumerThread);
        t1.start();
        t2.start();
        Thread.sleep(10000);
        producerThread.stop();
    }

}

