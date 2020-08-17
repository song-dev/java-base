package com.song.utils;

public class HexUtils {

    private final static char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 将b yte 数组转换为十六进制字符串
     *
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(HEX[(bytes[i] >> 4) & 0x0f]).append(HEX[bytes[i] & 0x0f]);
        }
        return sb.toString();
    }

    /**
     * 将十六进制字符串转换为 byte 数组
     *
     * @param content
     * @return
     */
    public static byte[] hexToBytes(String content) {
        char[] hex = content.toCharArray();
        int length = hex.length / 2;
        byte[] raw = new byte[length];
        for (int i = 0; i < length; i++) {
            int high = Character.digit(hex[i * 2], 16);
            int low = Character.digit(hex[i * 2 + 1], 16);
            int value = (high << 4) | low;
            if (value > 127) {
                value -= 256;
            }
            raw[i] = (byte) value;
        }
        return raw;
    }

}
