package com.song.utils.binhexoct;

import com.song.utils.hex.HexUtils;
import org.junit.Test;

public class BinHexOctTest {

    @Test
    public void test_bin_hex_oct() {

        // 十进制转成十六进制
        System.out.println(Integer.toHexString(10));
        // 十进制转成八进制
        System.out.println(Integer.toOctalString(10));
        // 十进制转成二进制
        System.out.println(Integer.toBinaryString(10));
        // 十六进制转成十进制
        System.out.println(Integer.valueOf("FF", 16).toString());
        // 八进制转成十进制
        System.out.println(Integer.valueOf("12", 8).toString());
        // 二进制转十进制
        System.out.println(Integer.valueOf("0101", 2).toString());

        System.out.println(Integer.parseInt("0", 10));
        System.out.println(Integer.parseInt("473", 10));
        System.out.println(Integer.parseInt("-0", 10));
        System.out.println(Integer.parseInt("-FF", 16));
        System.out.println(Integer.parseInt("1100110", 2));
        System.out.println(Integer.parseInt("2147483647", 10));
        System.out.println(Integer.parseInt("-2147483648", 10));
        System.out.println(Integer.parseInt("99", 16));

        byte[] bytes = BinHexOctUtils.intToBytes(1234);
        System.out.println(HexUtils.bytesToHex(bytes));
        System.out.println(BinHexOctUtils.bytesToInt(bytes));
        System.out.println(BinHexOctUtils.bytes2Int(bytes));

    }

    @Test
    public void test_hex() {

        System.out.println(Integer.toHexString(-10));
        System.out.println(String.format("%02x", 129));
        System.out.println(String.format("%d", 0x81));

    }

}
