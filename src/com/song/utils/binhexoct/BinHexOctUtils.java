package com.song.utils.binhexoct;

public class BinHexOctUtils {

    /**
     * 将字节数组转换为 int
     *
     * @param b 直接数组
     * @return int
     */
    public static int bytes2Int(byte[] b) {
        return (b[0] & 0xff) << 24 | (b[1] & 0xff) << 16 | (b[2] & 0xff) << 8 | (b[3] & 0xff);
    }

    /**
     * 将字节数组转换为 int
     *
     * @param b 直接数组
     * @return int
     */
    public static int bytesToInt(byte[] b) {
        int num = 0;
        for (int i = 0; i < 4; i++) {
            num <<= 8;
            num |= b[i] & 0xff;
        }
        return num;
    }

    /**
     * 将 int 转换为字节数组
     *
     * @param num 数字
     * @return 字节数组
     */
    public static byte[] intToBytes(int num) {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            b[i] = (byte) (num >>> (24 - i * 8));
        }
        return b;
    }

    /***
     * 16进制转ASCII
     * @param hex
     * @return
     */
    public static String convertHexToString(String hex) {
        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < hex.length() - 1; i += 2) {
            String output = hex.substring(i, (i + 2));
            int decimal = Integer.parseInt(output, 16);
            sb.append((char) decimal);
            temp.append(decimal);
        }
        return sb.toString();
    }

    /**
     * 中文转Unicode
     *
     * @param gbString
     * @return
     */
    public static String UnicodeEncoding(String gbString) {   //gbString = "测试"
        char[] utfBytes = gbString.toCharArray();   //utfBytes = [测, 试]
        String unicodeBytes = "";
        for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
            String hexB = Integer.toHexString(utfBytes[byteIndex]);   //转换为16进制整型字符串
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return unicodeBytes;
    }

    /**
     * Unicode转中文
     */
    public static String decodeUnicode(String dataStr) {
        int start = 0;
        int end = 0;
        StringBuffer buffer = new StringBuffer();
        while (start > -1) {
            end = dataStr.indexOf("\\u", start + 2);
            String charStr = "";
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length());
            } else {
                charStr = dataStr.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
            buffer.append(new Character(letter).toString());
            start = end;
        }
        return buffer.toString();
    }


    /**
     * gb2312编码
     */
    public static String gb2312decode(String string) throws Exception {
        byte[] bytes = new byte[string.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            byte high = Byte.parseByte(string.substring(i * 2, i * 2 + 1), 16);
            byte low = Byte.parseByte(string.substring(i * 2 + 1, i * 2 + 2), 16);
            bytes[i] = (byte) (high << 4 | low);
        }
        return new String(bytes, "gb2312");
    }

    /**
     * gb2312解码
     */
    public static String gb2312eecode(String string) throws Exception {
        StringBuffer gbkStr = new StringBuffer();
        byte[] gbkDecode = string.getBytes("gb2312");
        for (byte b : gbkDecode) {
            gbkStr.append(Integer.toHexString(b & 0xFF));
        }
        return gbkStr.toString();
    }

    /**
     * 和校验
     * SUM（cmd, Length, Data0…DataN）^0xFF
     */
    public static byte getCheckSum(byte[] packBytes) {
        int checkSum = 0;

        for (int i = 0; i < packBytes.length; i++) {
            checkSum += packBytes[i];
        }
        checkSum &= 0xff;
        return (byte) checkSum;
    }

}
