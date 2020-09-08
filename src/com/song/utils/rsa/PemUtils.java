package com.song.utils.rsa;

import com.song.utils.base64.Base64;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public class PemUtils {

    /**
     * pkcs8 格式转换 pem 格式
     *
     * @param rsaKey
     * @param isPublic
     * @return
     * @throws Exception
     */
    public static String pkcs8ToPem(byte[] rsaKey, boolean isPublic) throws Exception {
        String type;
        if (isPublic) {
            type = "PUBLIC KEY";
        } else {
            type = "PRIVATE KEY";
        }
        return asn1ToPem(rsaKey, type);
    }

    /**
     * pkcs1 格式转换 pem 格式
     *
     * @param rsaKey
     * @param isPublic
     * @return
     * @throws Exception
     */
    public static String pkcs1ToPem(byte[] rsaKey, boolean isPublic) throws Exception {
        String type;
        if (isPublic) {
            type = "RSA PUBLIC KEY";
        } else {
            type = "RSA PRIVATE KEY";
        }
        return asn1ToPem(rsaKey, type);
    }

    /**
     * 将 asn1 编码公私钥转换为 pem 格式
     *
     * @param rsaKey 公私钥
     * @param type   类型
     * @return
     * @throws Exception
     */
    private static String asn1ToPem(byte[] rsaKey, String type) throws Exception {
        PemObject pemObject = new PemObject(type, rsaKey);
        StringWriter stringWriter = new StringWriter();
        PemWriter pemWriter = new PemWriter(stringWriter);
        pemWriter.writeObject(pemObject);
        pemWriter.close();
        return stringWriter.toString();
    }

    /**
     * pem 转换 ASN1 格式
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String pemToAsn1(String key) throws Exception {
        PemReader reader = new PemReader(new InputStreamReader(
                new ByteArrayInputStream(key.getBytes(StandardCharsets.UTF_8))));
        PemObject pemObject = reader.readPemObject();
        return Base64.encode(pemObject.getContent());
    }

}
