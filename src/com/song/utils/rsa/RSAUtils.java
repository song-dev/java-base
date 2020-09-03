package com.song.utils.rsa;

import com.song.utils.base64.Base64;
import javafx.util.Pair;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA 最长分段数据
 * 算法   1024    2048    3072    4096
 * No    128     256     384     512
 * OAEP  62      190     318     446
 * ECB   117     245     373     501
 */
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
     * 可接受最长加密数据长度
     */
    public static final int MAX_ENCRYPT_OAEP_BLOCK = 318;

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
     * 公钥加密（非分段加密），OAEP Padding
     *
     * @param plainText         待加密明文
     * @param publicKeyOfBase64 base64 编码公钥
     * @return 加密后数据
     * @throws Exception
     */
    public static byte[] encryptWithOAEP(String plainText, String publicKeyOfBase64) throws Exception {
        return encrypt(plainText, publicKeyOfBase64, ALGORITHM_RSA_OAEP);
    }

    /**
     * 公钥加密（分段加密），OAEP Padding
     *
     * @param plainText         待加密明文
     * @param publicKeyOfBase64 base64 编码公钥
     * @return 加密后数据
     * @throws Exception
     */
    public static byte[] encryptWithOAEP(String plainText, String publicKeyOfBase64, int segmentSize) throws Exception {
        return encrypt(plainText, publicKeyOfBase64, segmentSize, ALGORITHM_RSA_OAEP);
    }

    /**
     * 公钥加密（非分段加密），PKCS1 Padding
     *
     * @param plainText         待加密明文
     * @param publicKeyOfBase64 base64 编码公钥
     * @return 加密后数据
     * @throws Exception
     */
    public static byte[] encryptWithECB(String plainText, String publicKeyOfBase64) throws Exception {
        return encrypt(plainText, publicKeyOfBase64, ALGORITHM_RSA_ECB);
    }

    /**
     * 公钥加密（分段加密），PKCS1 Padding
     *
     * @param plainText         待加密明文
     * @param publicKeyOfBase64 base64 编码公钥
     * @return 加密后数据
     * @throws Exception
     */
    public static byte[] encryptWithECB(String plainText, String publicKeyOfBase64, int segmentSize) throws Exception {
        return encrypt(plainText, publicKeyOfBase64, segmentSize, ALGORITHM_RSA_ECB);
    }

    /**
     * 公钥加密（非分段加密）
     *
     * @param plainText         待加密明文
     * @param publicKeyOfBase64 base64 编码公钥
     * @return 加密后数据
     * @throws Exception 异常
     */
    public static byte[] encrypt(String plainText, String publicKeyOfBase64, String algorithm) throws Exception {
        return encrypt(plainText, publicKeyOfBase64, -1, algorithm);
    }

    /**
     * 公钥加密（分段加密）
     *
     * @param plainText         待加密明文
     * @param publicKeyOfBase64 base64 编码公钥
     * @param segmentSize       分段大小
     * @return 加密后数据
     * @throws Exception 异常
     */
    public static byte[] encrypt(String plainText, String publicKeyOfBase64, int segmentSize, String algorithm) throws Exception {
        return encrypt(plainText.getBytes(StandardCharsets.UTF_8), publicKeyOfBase64, segmentSize, algorithm);
    }

    /**
     * 公钥加密（分段加密）
     *
     * @param plainText         待加密明文
     * @param publicKeyOfBase64 base64 编码公钥
     * @param segmentSize       分段大小
     * @return 加密后数据
     * @throws Exception 异常
     */
    public static byte[] encrypt(byte[] plainText, String publicKeyOfBase64, int segmentSize, String algorithm) throws Exception {
        RSAPublicKey publicKey = getPublicKey(publicKeyOfBase64);
        return encrypt(plainText, publicKey, segmentSize, algorithm);
    }

    /**
     * 公钥加密（分段加密）
     *
     * @param plainText   待加密明文
     * @param publicKey   公钥
     * @param segmentSize 分段大小
     * @return 加密后数据
     * @throws Exception
     */
    private static byte[] encrypt(byte[] plainText, RSAPublicKey publicKey, int segmentSize, String algorithm) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] cipherText;
        if (segmentSize > 0) {
            cipherText = doFinal(cipher, plainText, segmentSize);
        } else {
            cipherText = cipher.doFinal(plainText);
        }
        return cipherText;
    }

    /**
     * Base64 编码数据转换为 RSAPublicKey 对象
     *
     * @param publicKey Base64 编码数据
     * @return RSAPublicKey
     * @throws Exception
     */
    private static RSAPublicKey getPublicKey(String publicKey) throws Exception {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(publicKey));
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    /**
     * 私钥解密（非分段解密），OAEP Padding
     *
     * @param cipherText         待解密密文
     * @param privateKeyOfBase64 私钥
     * @return 解密后明文
     * @throws Exception
     */
    public static byte[] decryptWithOAEP(byte[] cipherText, String privateKeyOfBase64) throws Exception {
        return decrypt(cipherText, privateKeyOfBase64, ALGORITHM_RSA_OAEP);
    }

    /**
     * 私钥解密（分段解密），OAEP Padding
     *
     * @param cipherText         待解密密文
     * @param privateKeyOfBase64 私钥
     * @param segmentSize        分段大小
     * @return 解密后明文
     * @throws Exception
     */
    public static byte[] decryptWithOAEP(byte[] cipherText, String privateKeyOfBase64, int segmentSize) throws Exception {
        return decrypt(cipherText, privateKeyOfBase64, segmentSize, ALGORITHM_RSA_OAEP);
    }

    /**
     * 私钥解密（非分段解密），PKCS1 Padding
     *
     * @param cipherText         待解密密文
     * @param privateKeyOfBase64 私钥
     * @return 解密后明文
     * @throws Exception
     */
    public static byte[] decryptWithECB(byte[] cipherText, String privateKeyOfBase64) throws Exception {
        return decrypt(cipherText, privateKeyOfBase64, ALGORITHM_RSA_ECB);
    }

    /**
     * 私钥解密（分段解密），PKCS1 Padding
     *
     * @param cipherText         待解密密文
     * @param privateKeyOfBase64 私钥
     * @param segmentSize        分段大小
     * @return 解密后明文
     * @throws Exception
     */
    public static byte[] decryptWithECB(byte[] cipherText, String privateKeyOfBase64, int segmentSize) throws Exception {
        return decrypt(cipherText, privateKeyOfBase64, segmentSize, ALGORITHM_RSA_ECB);
    }

    /**
     * 私钥解密（非分段解密）
     *
     * @param cipherText         待解密密文
     * @param privateKeyOfBase64 私钥
     * @param algorithm          算法
     * @return 解密后明文
     * @throws Exception
     */
    public static byte[] decrypt(byte[] cipherText, String privateKeyOfBase64, String algorithm) throws Exception {
        return decrypt(cipherText, privateKeyOfBase64, -1, algorithm);
    }

    /**
     * 私钥解密（分段解密）
     *
     * @param cipherText         待解密密文
     * @param privateKeyOfBase64 私钥
     * @param segmentSize        分段大小
     * @param algorithm          算法
     * @return 解密后明文
     * @throws Exception
     */
    public static byte[] decrypt(byte[] cipherText, String privateKeyOfBase64, int segmentSize, String algorithm) throws Exception {
        RSAPrivateKey privateKey = getPrivateKey(privateKeyOfBase64);
        return decrypt(cipherText, privateKey, segmentSize, algorithm);
    }

    /**
     * 私钥解密（分段解密）
     *
     * @param cipherText  待解密密文
     * @param privateKey  私钥
     * @param segmentSize 分段大小
     * @param algorithm   算法
     * @return 解密后明文
     * @throws Exception
     */
    private static byte[] decrypt(byte[] cipherText, RSAPrivateKey privateKey, int segmentSize, String algorithm) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] plainText;
        if (segmentSize > 0) {
            plainText = doFinal(cipher, cipherText, segmentSize);
        } else {
            plainText = cipher.doFinal(cipherText);
        }
        return plainText;
    }

    /**
     * base64 编码私钥转换为 RSAPrivateKey 对象
     *
     * @param privateKey base64 编码私钥
     * @return RSAPrivateKey
     * @throws Exception
     */
    private static RSAPrivateKey getPrivateKey(String privateKey) throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }

    /**
     * 分段加解密
     *
     * @param cipher      Cipher
     * @param bytes       待加解密数据
     * @param segmentSize 分段大小
     * @return 结果
     * @throws Exception
     */
    private static byte[] doFinal(Cipher cipher, byte[] bytes, int segmentSize) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int bytesLen = bytes.length;
        int offSet = 0;
        byte[] buffer;
        int i = 0;
        while (bytesLen - offSet > 0) {
            if (bytesLen - offSet > segmentSize) {
                buffer = cipher.doFinal(bytes, offSet, segmentSize);
            } else {
                buffer = cipher.doFinal(bytes, offSet, bytesLen - offSet);
            }
            out.write(buffer, 0, buffer.length);
            i++;
            offSet = i * segmentSize;
        }
        byte[] data = out.toByteArray();
        out.close();
        return data;
    }

    /**
     * 私钥签名，签名生成字符串长度相同, 被签名数据没有字节大小限制
     *
     * @param data       待签名数据
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    public static byte[] sign(byte[] data, String privateKey) throws Exception {
        Signature signature = Signature.getInstance(ALGORITHM_RSA_SIGN);
        signature.initSign(getPrivateKey(privateKey));
        signature.update(data);
        return signature.sign();
    }

    /**
     * 公钥验签
     *
     * @param data
     * @param sign
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, byte[] sign, String publicKey) throws Exception {
        Signature signature = Signature.getInstance(ALGORITHM_RSA_SIGN);
        signature.initVerify(getPublicKey(publicKey));
        signature.update(data);
        return signature.verify(sign);
    }

}
