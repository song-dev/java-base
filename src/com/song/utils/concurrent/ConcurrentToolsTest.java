package com.song.utils.concurrent;

import org.junit.Test;

import javax.swing.text.html.parser.Entity;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 并发工具类测试
 */
public class ConcurrentToolsTest {

    /**
     * 线程执行完成就销毁，但是阻塞线程需要所有令牌才能被唤醒
     */
    @Test
    public void test_count_down_latch() throws InterruptedException {
        CountDownLatch count = new CountDownLatch(2);
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName());
                    count.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        count.await();
        System.out.println(Thread.currentThread().getName());
    }

    /**
     * 线程执行完成就阻塞，等待所有线程阻塞后
     *
     * @throws InterruptedException 异常
     */
    @Test
    public void test_cyclic_barrier() throws InterruptedException {
        ConcurrentHashMap<String, Integer> hashMap = new ConcurrentHashMap<>();
        CyclicBarrier barrier = new CyclicBarrier(10, () -> {
            int result = 0;
            for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
                result += entry.getValue();
            }
            System.out.println("End: " + Thread.currentThread().getId() + ", " + result);
        });
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(100);
                    hashMap.put(Thread.currentThread().getName(), 1);
                    System.out.println(Thread.currentThread().getId());
                    barrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        System.out.println(Thread.currentThread().getName());
        Thread.sleep(3000);
    }

    @Test
    public void test_semaphore() throws InterruptedException {
        Semaphore semaphore = new Semaphore(10);
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName());
                    semaphore.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        Thread.sleep(5000);
        System.out.println(Thread.currentThread().getName());
    }

    @Test
    public void test_exchanger() throws InterruptedException {
        Exchanger<String> exchanger = new Exchanger<String>();
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName());
                Object exchange = exchanger.exchange(Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getName() + ": " + exchange);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName());
                Object exchange = exchanger.exchange(Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getName() + ": " + exchange);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(2000);
        System.out.println(Thread.currentThread().getName());
    }


}
