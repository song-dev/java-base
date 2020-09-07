package com.song.utils.aes;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class AesGcmUtils {

    private static final String IV_DEFAULT = "000000000000";
    private static final String ALGORITHM_AES = "AES";
    private static final String ALGORITHM_AES_GCM = "AES/GCM/NoPadding";


    public static byte[] encrypt(String plainText, String aesKey, String iv) throws Exception {
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(16 * 8, iv.getBytes(StandardCharsets.UTF_8));
        SecretKeySpec key = new SecretKeySpec(aesKey.getBytes(), ALGORITHM_AES);
        Cipher cipher = Cipher.getInstance(ALGORITHM_AES_GCM);
        cipher.init(Cipher.ENCRYPT_MODE, key, gcmParameterSpec);
        return cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] decrypt(byte[] cipherText, String aesKey, String iv) throws Exception {
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(16 * 8, iv.getBytes(StandardCharsets.UTF_8));
        SecretKeySpec key = new SecretKeySpec(aesKey.getBytes(), ALGORITHM_AES);
        Cipher cipher = Cipher.getInstance(ALGORITHM_AES_GCM);
        cipher.init(Cipher.DECRYPT_MODE, key, gcmParameterSpec);
        return cipher.doFinal(cipherText);
    }

}
