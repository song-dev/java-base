package com.song.utils.rsa;

import com.song.utils.base64.Base64;
import javafx.util.Pair;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import javax.crypto.Cipher;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtils {

    /**
     * 指定加密算法为RSA
     */
    private static final String ALGORITHM_RSA = "RSA";
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
    private static final int KEY_SIZE = 3072;

    /**
     * 生成RSA密钥对
     *
     * @return
     */
    public static Pair<String, String> getRSAPair() throws Exception {
        SecureRandom secureRandom = new SecureRandom();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM_RSA);
        keyPairGenerator.initialize(KEY_SIZE, secureRandom);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        Key publicKey = keyPair.getPublic();
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

    /**
     * 公钥加密
     *
     * @param plainText
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static byte[] encryptWithOAEP(String plainText, RSAPublicKey publicKey) throws Exception {
        return encrypt(plainText, publicKey, ALGORITHM_RSA_OAEP);
    }

    /**
     * 公钥加密
     *
     * @param plainText
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static byte[] encryptWithECB(String plainText, RSAPublicKey publicKey) throws Exception {
        return encrypt(plainText, publicKey, ALGORITHM_RSA_ECB);
    }

    /**
     * 公钥加密
     *
     * @param plainText
     * @param publicKey
     * @param algorithm
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(String plainText, RSAPublicKey publicKey, String algorithm) throws Exception {
        return encrypt(plainText.getBytes(StandardCharsets.UTF_8), publicKey, algorithm);
    }

    /**
     * 公钥加密
     *
     * @param plainText
     * @param publicKey
     * @param algorithm
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] plainText, RSAPublicKey publicKey, String algorithm) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(plainText);
    }

    /**
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static RSAPublicKey getPublicKey(String publicKey) throws Exception {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(publicKey));
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    /**
     * 私钥解密
     *
     * @param cipherText
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] decryptWithOAEP(byte[] cipherText, RSAPrivateKey privateKey) throws Exception {
        return decrypt(cipherText, privateKey, ALGORITHM_RSA_OAEP);
    }

    /**
     * 私钥解密
     *
     * @param cipherText
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] decryptWithECB(byte[] cipherText, RSAPrivateKey privateKey) throws Exception {
        return decrypt(cipherText, privateKey, ALGORITHM_RSA_ECB);
    }

    /**
     * 私钥解密
     *
     * @param cipherText
     * @param privateKey
     * @param algorithm
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] cipherText, RSAPrivateKey privateKey, String algorithm) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(cipherText);
    }

    /**
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }

}
