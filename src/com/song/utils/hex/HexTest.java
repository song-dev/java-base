package com.song.utils.hex;

import org.junit.Test;

public class HexTest {

    @Test
    public void test_hex() {
        String encode = HexBin.encode("abc123xyzXYZ,./".getBytes());
        System.out.println(encode);

        System.out.println(new String(HexBin.decode(encode)));

        String hex = HexUtils.bytesToHex("abc123xyzXYZ,./".getBytes());
        System.out.println(hex);

        System.out.println(new String(HexUtils.hexToBytes(hex)));
    }

}
