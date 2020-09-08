package com.song.utils.rsa;

import com.song.utils.base64.Base64;
import com.song.utils.hex.HexUtils;
import org.junit.Test;

import java.nio.ByteBuffer;

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

        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOROSt+3cN0A1OnV" +
                "A/bFVVsxzP/Aljzr9mNoDBBEKQA+zDDmZfkoL6MV+ym7fIdL+7VVCicTrKaYC8yx" +
                "tpltCVhqtGFjRHuM3K4kUgQMU0QZO5+ShfRGRa4tQaNQcRGyOnStLTyY2kNDMoz+" +
                "N0snQx6Ap9Far6nKCsslz4pG7obzAgMBAAECgYBh1l9l0eHvA606a8vgE57JjuTv" +
                "8OMxRll3Lh6FVgBQF5k/l2y5wovvkrronmz2OgyrH79TEa5aUiw0UzASPdTTnOWC" +
                "FPqIEH48ajZ3+V7Ubn6JvpqizW1NjDwtWRg8Qs2AmXmJoxd7X5V2Ekr0u9n5k7qu" +
                "mEgcxLTNd+K3rU1g8QJBAPTbrl5b4diMZTLYcmaC1By1XokMdNEP1L6AjdcQcXQw" +
                "8Bp/iVljzq0RWus0gqzX07iKe2F/ARy830/93EAWQQsCQQDuscu+hk+bVdPrHRD6" +
                "raoxa42xxiAjyI75kmioJrkZXnMSb4rvT9IuOif3v0Hwl04xh4QDuIUxddNvzNpM" +
                "jFK5AkAzjjnFpmUEOnNoZEiaKvvMW6ffBV9nUYUC44B6Av1QcKlteuMJjklLCXdy" +
                "RKkrCXqLxaS634n1ahM+/X2thMIHAkEAj6KD9XGDQ/lA3fGOvxp0GOscyAZuXroY" +
                "d0xIGC+9IOv6GPTwSRPiaZjrZiU8O9gA4MMoiMintdyuUE+YaYnjWQJAUMKivMzC" +
                "htawmnEtiOtwiu4dH2pCHmxBOXPGNyrmH28epPnpAkc2sbvB6CGftldYjoVbv9+k" +
                "ZBXsWDbOkSCyMw==";

        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDkTkrft3DdANTp1QP2xVVbMcz/" +
                "wJY86/ZjaAwQRCkAPsww5mX5KC+jFfspu3yHS/u1VQonE6ymmAvMsbaZbQlYarRh" +
                "Y0R7jNyuJFIEDFNEGTufkoX0RkWuLUGjUHERsjp0rS08mNpDQzKM/jdLJ0MegKfR" +
                "Wq+pygrLJc+KRu6G8wIDAQAB";

        String pkcs1_publicKey = "MIGJAoGBAOROSt+3cN0A1OnVA/bFVVsxzP/Aljzr9mNoDBBEKQA+zDDmZfkoL6MV" +
                "+ym7fIdL+7VVCicTrKaYC8yxtpltCVhqtGFjRHuM3K4kUgQMU0QZO5+ShfRGRa4t" +
                "QaNQcRGyOnStLTyY2kNDMoz+N0snQx6Ap9Far6nKCsslz4pG7obzAgMBAAE=";

        byte[] asn1Key = Base64.decode(pkcs1_publicKey);
        System.out.println(HexUtils.bytesToHex(asn1Key));

        System.out.println(parsePkcs8PublicKey(publicKey));
//        System.out.println(parsePkcs1PublicKey(pkcs1_publicKey));

    }

    private String parsePkcs8PublicKey(String publicKey) {

        // 1024
        ByteBuffer buffer = ByteBuffer.wrap(Base64.decode(publicKey));
        System.out.println("结构节点: " + Integer.toHexString(buffer.get()));
        System.out.println("表示长度: " + Integer.toHexString(buffer.get()));
        int n_len = buffer.get() + 256;
        System.out.println("剩余长度: " + n_len + ", " + Integer.toHexString(n_len));
        System.out.println("结构节点: " + Integer.toHexString(buffer.get()));
        int b_len = buffer.get();
        System.out.println("表示长度: " + b_len + ", " + Integer.toHexString(b_len));
        byte[] dest = new byte[b_len];
        buffer.get(dest);
        System.out.println(HexUtils.bytesToHex(dest));
        System.out.println("二进制串: " + Integer.toHexString(buffer.get()));
        System.out.println("表示长度: " + Integer.toHexString(buffer.get()));
        System.out.println("剩余长度: " + Integer.toHexString(buffer.get()) + ", " + buffer.remaining());
        System.out.println("头结束符: " + Integer.toHexString(buffer.get()));
        System.out.println("结构节点: " + Integer.toHexString(buffer.get()));
        System.out.println("表示长度: " + Integer.toHexString(buffer.get()));
        System.out.println("剩余长度: " + Integer.toHexString(buffer.get()) + ", " + buffer.remaining());
        System.out.println("模数内容标识符: " + Integer.toHexString(buffer.get()));
        System.out.println("表示长度: " + Integer.toHexString(buffer.get()));
        System.out.println("剩余长度: " + Integer.toHexString(buffer.get()) + ", " + buffer.remaining());
        System.out.println("结束符: " + Integer.toHexString(buffer.get()));
        byte[] n = new byte[128];
        buffer.get(n);
        System.out.println("模数内容: " + HexUtils.bytesToHex(n));
        System.out.println("指数内容标识符: " + Integer.toHexString(buffer.get()));
        System.out.println("指数长度: " + Integer.toHexString(buffer.get()) + ", " + buffer.remaining());
        byte[] e = new byte[buffer.remaining()];
        buffer.get(e);
        System.out.println("指数: " + HexUtils.bytesToHex(e));
        return HexUtils.bytesToHex(n);

    }

    private String parsePkcs1PublicKey(String publicKey) {

        // 1024
        ByteBuffer buffer = ByteBuffer.wrap(Base64.decode(publicKey));
        System.out.println("结构节点: " + Integer.toHexString(buffer.get()));
        System.out.println("表示长度: " + Integer.toHexString(buffer.get()));
        System.out.println("剩余长度: " + Integer.toHexString(buffer.get()) + ", " + buffer.remaining());
        System.out.println("模数内容标识符: " + Integer.toHexString(buffer.get()));
        System.out.println("表示长度: " + Integer.toHexString(buffer.get()));
        int b_len = buffer.get() + 256;
        System.out.println("内容长度: " + b_len + ", " + Integer.toHexString(b_len));
        System.out.println("头结束符: " + Integer.toHexString(buffer.get()));
        byte[] n = new byte[128];
        buffer.get(n);
        System.out.println("模数内容: "+HexUtils.bytesToHex(n));
        System.out.println("指数内容标识符: " + Integer.toHexString(buffer.get()));
        System.out.println("指数长度: " + Integer.toHexString(buffer.get()) + ", " + buffer.remaining());
        byte[] e = new byte[buffer.remaining()];
        buffer.get(e);
        System.out.println("指数: " + HexUtils.bytesToHex(e));
        return HexUtils.bytesToHex(n);

    }

}
