package com.song.utils.aes;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * - AES 加解密 BlockSize 固定为 16 字节（128 位）
 * - AES 加解密 IV 长度和 BlockSize 大小相等
 * - AES 加解密支持 128 位、196 位、256 位。TODO Android 默认库只支持 128 位
 * - AES 加解密支持 CBC、ECB 模式，NoPadding 和 PKCS5Padding
 */
public final class AESUtils {

    private static final String ALGORITHM_AES = "AES";
    private static final String ALGORITHM_AES_PKCS5_PADDING = "AES/CBC/PKCS5Padding";
    private static final String IV = "0000000000000000";

    /**
     * 使用默认 IV 加密
     *
     * @param plainText
     * @param aesKey
     * @return
     * @throws Exception
     */
    public static byte[] encryptWithDefaultIv(String plainText, String aesKey) throws Exception {
        return encrypt(plainText, aesKey, IV);
    }

    /**
     * @param plainText
     * @param aesKey
     * @param iv
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(String plainText, String aesKey, String iv) throws Exception {
        return encrypt(plainText.getBytes(StandardCharsets.UTF_8), aesKey.getBytes(StandardCharsets.UTF_8),
                iv.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * @param plainText
     * @param aesKey
     * @param iv
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] plainText, byte[] aesKey, byte[] iv) throws Exception {
        IvParameterSpec spec = new IvParameterSpec(iv);
        SecretKeySpec secretKey = new SecretKeySpec(aesKey, "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHM_AES_PKCS5_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);
        return cipher.doFinal(plainText);
    }

    /**
     * 使用默认 IV 解密
     *
     * @param cipherText
     * @param aesKey
     * @return
     * @throws Exception
     */
    public static byte[] decryptWithDefaultIv(byte[] cipherText, String aesKey) throws Exception {
        return decrypt(cipherText, aesKey.getBytes(StandardCharsets.UTF_8), IV.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * @param cipherText
     * @param aesKey
     * @param iv
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] cipherText, String aesKey, String iv) throws Exception {
        return decrypt(cipherText, aesKey.getBytes(StandardCharsets.UTF_8), iv.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * @param cipherText
     * @param aesKey
     * @param iv
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] cipherText, byte[] aesKey, byte[] iv) throws Exception {
        IvParameterSpec spec = new IvParameterSpec(iv);
        SecretKeySpec secretKey = new SecretKeySpec(aesKey, ALGORITHM_AES);
        Cipher cipher = Cipher.getInstance(ALGORITHM_AES_PKCS5_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
        return cipher.doFinal(cipherText);
    }

}
