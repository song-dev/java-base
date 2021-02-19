package com.song.utils.collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class CollectionTest {

    private long current;

    @Before
    public void test_before() {
        current = System.currentTimeMillis();
    }

    @After
    public void test_after() {
        System.out.println("耗时: " + (System.currentTimeMillis() - current));
    }

    @Test
    public void test() {
        // 集合的分类，各种集合底层数据实现，使用场景

        // 特殊集合的使用

        // 迭代器

        new HashMap<>();
        new ArrayList<>();

        // 集合安全


    }

}
