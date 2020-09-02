package com.song.utils.base64;

import org.junit.Test;

import java.security.SecureRandom;

/**
 * 概念: Base64是一种基于64个可打印字符来表示二进制数据的表示方法
 * 描述: 由于 log64=6，所以每6个比特为一个单元，对应某个可打印字符。
 * 3个字节相当于24个比特，对应于4个Base64单元，即3个字节可由4个可打印字符来表示。它可用来作为电子邮件的传输编码。
 * 在Base64中的可打印字符包括字母A-Z、a-z、数字0-9，这样共有62个字符，此外两个可打印符号在不同的系统中而不同。
 */
public class Base64Test {

    @Test
    public void test_base64() {

        byte[] data = new byte[128];
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 128; i++) {
            data[i] = (byte) random.nextInt(1000);
        }

        String encode = Base64.encode(data);
        System.out.println(encode);

        System.out.println(java.util.Base64.getUrlEncoder().encodeToString(data));
        System.out.println(java.util.Base64.getMimeEncoder().encodeToString(data));

        byte[] decode = Base64.decode(encode);
        System.out.println(new String(decode));
    }

}
