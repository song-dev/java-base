package com.song.utils.concurrent;

import org.junit.Test;

import java.util.concurrent.atomic.*;

/**
 * 原子操作类测试
 */
public class AtomicTest {

    @Test
    public void test() throws InterruptedException {
        final int[] value = {0};
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                value[0]++;
                value[0]++;
                System.out.println(Thread.currentThread().getName() + ": " + value[0]);
            }).start();
        }
        Thread.sleep(10);
        System.out.println(value[0]);
    }

    @Test
    public void test_integer() throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                atomicInteger.getAndIncrement();
                System.out.println(Thread.currentThread().getName() + ": " + atomicInteger.get());
            }).start();
        }
        Thread.sleep(10);
        System.out.println(atomicInteger.get());
    }

    @Test
    public void test_array() throws InterruptedException {
        long[] aLong = new long[]{0L, 2L};
        AtomicLongArray atomicLongArray = new AtomicLongArray(aLong);
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                atomicLongArray.getAndIncrement(0);
                System.out.println(Thread.currentThread().getName() + ": " + atomicLongArray.get(0));
            }).start();
        }
        Thread.sleep(10);
        System.out.println(atomicLongArray.get(0));
    }

    @Test
    public void test_reference() throws InterruptedException {
        User joy = new User(0, "joy");
        AtomicReference<User> atomicReference = new AtomicReference<User>(joy);
        atomicReference.compareAndSet(joy, new User(10,"anna"));
        Thread.sleep(10);
        System.out.println(atomicReference.get().toString());
    }

    @Test
    public void test_field_updater() throws InterruptedException {
        AtomicIntegerFieldUpdater<User> id = AtomicIntegerFieldUpdater.newUpdater(User.class,"id");
        User joy = new User(0, "joy");
        id.getAndIncrement(joy);
        System.out.println(id.get(joy));
    }

}
