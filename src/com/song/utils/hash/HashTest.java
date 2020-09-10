package com.song.utils.hash;

import org.junit.Test;

import java.nio.charset.StandardCharsets;

public class HashTest {

    @Test
    public void test_hash() throws Exception {
        String content = "test";
        System.out.println(HashUtils.md5Encode(content.getBytes(StandardCharsets.UTF_8)));
        System.out.println(HashUtils.sha1Encode(content.getBytes(StandardCharsets.UTF_8)));
        System.out.println(HashUtils.sha256Encode(content.getBytes(StandardCharsets.UTF_8)));
    }

}
