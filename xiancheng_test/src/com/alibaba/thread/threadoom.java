package com.alibaba.thread;

public class threadoom implements Runnable {

    private static Object threadoom;

    public static void main(String[] args) {
        com.alibaba.thread.threadoom a = new threadoom();
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(a);
            thread.start();

        }

    }


    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < 100; i++) {
                System.out.println(i);
            }
        }
    }
}
