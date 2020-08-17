package com.song.utils;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import javafx.util.Pair;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.junit.Test;

import javax.crypto.Cipher;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtils {

    /**
     * 指定加密算法为RSA
     */
    private static final String ALGORITHM = "RSA";
    /**
     * ECB 加密模式
     */
    private static final String ALGORITHM_RSA_ECB = "RSA/ECB/PKCS1Padding";
    /**
     * OAEP 加密模式
     */
    private static final String ALGORITHM_RSA_OAEP = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
    /**
     * 签名模式
     */
    private static final String ALGORITHM_RSA_SIGN = "SHA256withRSA";
    /**
     * 密钥长度，用来初始化
     */
    private static final int KEY_SIZE = 1024;

    public static byte[] encryptWithOAEP(String plainText, RSAPublicKey publicKey) throws Exception {
        return encrypt(plainText, publicKey, ALGORITHM_RSA_OAEP);
    }

    public static byte[] encryptWithECB(String plainText, RSAPublicKey publicKey) throws Exception {
        return encrypt(plainText, publicKey, ALGORITHM_RSA_ECB);
    }

    private static byte[] encrypt(String plainText, RSAPublicKey publicKey, String algorithm) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
    }

    public static RSAPublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes = Base64.decode(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    /**
     * 生成RSA密钥对
     *
     * @return
     */
    public static Pair<String, String> getRSAPair() throws Exception {
        // RSA算法要求有一个可信任的随机数源
        SecureRandom secureRandom = new SecureRandom();
        // 为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        // 利用上面的随机数据源初始化这个KeyPairGenerator对象
        keyPairGenerator.initialize(KEY_SIZE, secureRandom);
        // 生成密匙对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 得到公钥
        Key publicKey = keyPair.getPublic();
        // 得到私钥
        Key privateKey = keyPair.getPrivate();
        byte[] publicKeyBytes = publicKey.getEncoded();
        byte[] privateKeyBytes = privateKey.getEncoded();
        String publicKeyBase64 = Base64.encode(publicKeyBytes);
        String privateKeyBase64 = Base64.encode(privateKeyBytes);
        return new Pair<>(publicKeyBase64, privateKeyBase64);
    }

    /**
     * pkcs1格式转换pem格式
     *
     * @param rsaKey
     * @param isPublic
     * @return
     * @throws Exception
     */
    public static String pkcs1ToPem(byte[] rsaKey, boolean isPublic) throws Exception {
        String type;
        if (isPublic) {
            type = "PUBLIC KEY";
        } else {
            type = "RSA PRIVATE KEY";
        }
        PemObject pemObject = new PemObject(type, rsaKey);
        StringWriter stringWriter = new StringWriter();
        PemWriter pemWriter = new PemWriter(stringWriter);
        pemWriter.writeObject(pemObject);
        pemWriter.close();
        return stringWriter.toString();
    }

}
