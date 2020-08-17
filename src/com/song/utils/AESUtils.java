package com.song.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {

    private static final String ALGORITHM_AES = "AES";
    private static final String ALGORITHM_AES_PKCS5_PADDING = "AES/CBC/PKCS5Padding";
    private static final String ALGORITHM_AES_NO_PADDING = "AES/CBC/NoPadding";

    public static byte[] decrypt(String aes_key, String iv, byte[] content) throws Exception {
        IvParameterSpec spec = new IvParameterSpec(iv.getBytes());
        SecretKeySpec secretKey = new SecretKeySpec(aes_key.getBytes(), ALGORITHM_AES);
        Cipher cipher = Cipher.getInstance(ALGORITHM_AES_NO_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
        return cipher.doFinal(content);
    }

    public static byte[] encrypt(String aes_key, String iv, String content) throws Exception {
        IvParameterSpec spec = new IvParameterSpec(iv.getBytes());
        SecretKeySpec secretKey = new SecretKeySpec(aes_key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHM_AES_NO_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);
        return cipher.doFinal(content.getBytes());
    }

}
