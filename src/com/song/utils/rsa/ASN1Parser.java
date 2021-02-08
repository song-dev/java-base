package com.song.utils.rsa;

import com.song.utils.base64.Base64;
import com.song.utils.hex.HexUtils;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * 解析 ASN1 编码 RSA 秘钥数据
 */
public class ASN1Parser {

    String pkcs8PublicKey1024 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCz1JiHxroOf83uSGF9Ud87iaIh" +
            "2SwDOden82xk+Zz6Ll3e4EECf+ZDY0kluCm90hvlrKhDMQLmc45S+WQ2aLLXCuDZ" +
            "USTynffn/Eushc35gMXIHVkGyqb66NhxYUe6sikHanfsIKGnsA3wgeCOTbrKbrbD" +
            "JtCdXyMYU3snwRvh5QIDAQAB";

    String pkcs1PublicKey1024 = "MIGJAoGBAOROSt+3cN0A1OnVA/bFVVsxzP/Aljzr9mNoDBBEKQA+zDDmZfkoL6MV" +
            "+ym7fIdL+7VVCicTrKaYC8yxtpltCVhqtGFjRHuM3K4kUgQMU0QZO5+ShfRGRa4t" +
            "QaNQcRGyOnStLTyY2kNDMoz+N0snQx6Ap9Far6nKCsslz4pG7obzAgMBAAE=";

    String pkcs8PrivateKey4096 = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAzBvQSbqVaYCQN02L/yE5" +
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

    String pkcs1PrivateKey4096 = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOROSt+3cN0A1OnV" +
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


    @Test
    public void test_pem_parse() throws Exception {
        // 1024 pkcs8 PublicKey
        byte[] asn1Key = Base64.decode(pkcs8PublicKey1024);
        String pkcs8OfPem = PemUtils.pkcs8ToPem(asn1Key, true);
        System.out.println("pkcs8PublicKey1024 PEM: \n" + pkcs8OfPem);
        System.out.println("pkcs8PublicKey1024: \n" + PemUtils.pemToAsn1(pkcs8OfPem));


        System.out.println("pkcs1PublicKey1024 PEM: \n" + PemUtils.pkcs1ToPem(Base64.decode(pkcs1PublicKey1024), true));
        System.out.println("pkcs8PrivateKey4096 PEM: \n" + PemUtils.pkcs8ToPem(Base64.decode(pkcs8PrivateKey4096), true));
        System.out.println("pkcs1PrivateKey4096 PEM: \n" + PemUtils.pkcs1ToPem(Base64.decode(pkcs1PrivateKey4096), true));
    }

    @Test
    public void parse() throws Exception {
        // 验证 位数、公私钥、PKCS1

//        parsePkcs1PublicKey(pkcs1PublicKey1024);
        parsePkcs8PublicKey(pkcs8PublicKey1024);

    }

    private String parsePkcs8PublicKey(String publicKey) {
        // 1024
        ByteBuffer buffer = ByteBuffer.wrap(Base64.decode(publicKey));
        System.out.println("总长度: " + buffer.remaining());
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

    private void parsePkcs1PublicKey(String publicKey) {

        // 1024
        byte[] decode = Base64.decode(publicKey);
        System.out.println("总长度: " + decode.length);
        ByteBuffer buffer = ByteBuffer.wrap(decode);
        System.out.println("结构节点: " + Integer.toHexString(buffer.get()));
        {
            byte one = buffer.get();
            int one_l = 0;
            if (one < 0) {
                one_l = one & 0xff;
            } else {
                one_l = one;
            }
            System.out.println("表示长度: " + (one_l) + " " + Integer.toHexString(one_l));
        }
        {
            byte two = buffer.get();
            int two_l = 0;
            if (two < 0) {
                two_l = two & 0xff;
            } else {
                two_l = two;
            }
            System.out.println("剩余长度: " + two_l + ", hex: " + Integer.toHexString(two_l) + ", " + buffer.remaining());
        }
        System.out.println("模数内容标识符: " + Integer.toHexString(buffer.get()));
        {
            byte three = buffer.get();
            int three_l = 0;
            if (three < 0) {
                three_l = three & 0xff;
            } else {
                three_l = three;
            }
            System.out.println("表示长度: " + three_l + ", hex: " + Integer.toHexString(three_l) + ", " + buffer.remaining());
        }
        {
            byte four = buffer.get();
            int four_l = 0;
            if (four < 0) {
                four_l = four & 0xff;
            } else {
                four_l = four;
            }
            System.out.println("内容长度: " + four_l + ", hex: " + Integer.toHexString(four_l) + ", " + buffer.remaining());
        }
        System.out.println("头结束符: " + Integer.toHexString(buffer.get()));
        {
            byte[] n = new byte[128];
            buffer.get(n);
            System.out.println("模数内容: " + HexUtils.bytesToHex(n));
        }
        System.out.println("指数内容标识符: " + Integer.toHexString(buffer.get()));
        System.out.println("指数长度: " + Integer.toHexString(buffer.get()) + ", " + buffer.remaining());
        byte[] e = new byte[buffer.remaining()];
        buffer.get(e);
        System.out.println("指数: " + HexUtils.bytesToHex(e));

    }

}
