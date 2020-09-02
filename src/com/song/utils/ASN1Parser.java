package com.song.utils;

import org.junit.Test;

/**
 * 解析 ASN1 编码 RSA 秘钥数据
 */
public class ASN1Parser {

    @Test
    public void parse() throws Exception {

        // pkcs8
        String key = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAzBvQSbqVaYCQN02L/yE5" +
                "0guCk59gpkSrshMowT1ng39omm08RIoTWDpS4dZMsz3tU6M2bB61KeACOKoXWaBv" +
                "2eZL+fLWwL1gSkhrA00mXp2x6nhOtjS930JYNLleWVm1d9ip9+ujaetRRojflHnT" +
                "pGWsc6btTXzvlQMgoz4Nf+S32Wnc/9ChqA37lL+b/TMUIu2D5H/LTwj2jLVZS80k" +
                "Fuk4gGt0QDyuOMEUHwYkXBAqM87Pp36R36CWIupu3b9WcMRlrVrcX6JTcHPWE3r/" +
                "2vIMwiCSt3vC8UPwVrRsn8KIn2GvcgZweVEurjMhp7D7fzcrtAbCvQOGlnRqpvaU" +
                "ZtDZitiT9KOe9Zyhw8FYjoalZaQxfTWob6Ikpl0BDN1Abk9U8+t5qocfAtC7FiO0" +
                "9epKvl/p/eQpBp7ow1+uSuVi1RDdPuBMSMY5kFJDTxl8UEQQ07/OzQc3xay5j0WW" +
                "UJW/BnnoS3QtzUPtXRd0FNz+o43E0MhH6GvnGWvf5a6Y+DI92euFioIv28gyV2O4" +
                "1/3d5jtqN37MUlPe56lVFA34YTj802Zxf5rUHUVm8KtAzy5mFxHMtXAGDYtGa6EM" +
                "tCIclF/ewu06bh7uTrTwMsjxRS+PRs9LWbacI45NV/UyogBxD/uQBwcissDUWG3l" +
                "Yh7XpKIpWsx/xHH2sGWfNX8CAwEAAQ==";

        byte[] asn1Key = Base64.decode(key);
        String hex = byteToHex(asn1Key);
        System.out.println(hex);
    }

    private final static char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 将 byte数组转换为十六进制文本
     *
     * @param data
     * @return
     */
    public static String byteToHex(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            sb.append(HEX[(data[i] >> 4) & 0x0f]).append(HEX[data[i] & 0x0f]);
            sb.append(' ');
        }
        return sb.toString();
    }

}
