package com.song.utils.aes;

import com.song.utils.base64.Base64;
import com.song.utils.binhexoct.BinHexOctUtils;
import com.song.utils.hex.HexUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;

public class AESTest {

    @Test
    public void test_aes() throws Exception {
        String key = "0123456789012345";
        String iv = "0123456789012345";

        String content = "Optimizing the CPU utilization of an application has many advantages, such as providing " +
                "a faster and smoother user experience and extending the battery life of the device." +
                "You can use the CPU Profiler to check your application's CPU usage and thread activity " +
                "in real time while interacting with your application, as well as check the details " +
                "of the recorded method trace data, function trace data, and system trace data.";

        byte[] encrypt = AESUtils.encrypt(content, key, iv);
        System.out.println(Base64.encode(encrypt));
        System.out.println(java.util.Base64.getUrlEncoder().encodeToString(encrypt));
        System.out.println(new String(AESUtils.decrypt(encrypt, key, iv)));
    }

    @Test
    public void test_aes_decrypt_gid() throws Exception {
        String key = "fCw53lwNFFNCflhF1VKiYTQ6NdHl3WHg";
        String iv = "TsGqMygT8RfSJYEf";
        String content = "KIn2ZzUETZ5DtqI/RJOhuR3E6xVjryIp63DnmOoSP5qk95m9zkzJ03SNLr/o/q+ZO5+XF5kFQiUr72t49/kaAam6yUSFOjJkMmwSKtJTvzt6QbXOZ1NB3tU5Z2+0c/8Mzv0wbKAaB/1UP4T+hKeDHg==";

        System.out.println(new String(AESUtils.decrypt(Base64.decode(content), key, iv)));
    }

    @Test
    public void test_aes_decrypt_bid() throws Exception {
        String key = "30212102dicudiab";
        String iv = "30212102dicudiab";
        String content = "FoNYV5uSyQTXlBkXdEP1dPWS0XaTgvdgYndVm88gzN3Qx/pr2nPLDsIYiLldj63gWn0KsJYTBhJqqf/n9KLFMDhWBn1Q9/nRGlXSTBylLMs7zXkm9SX2uPkfOjnk62b2";

        System.out.println(new String(AESUtils.decrypt(Base64.decode(content), key, iv)));
    }

    @Test
    public void test_aes_gcm() throws Exception {
        String key = "0123456789012345";
        String iv = "000000000000";

        String content = "Optimizing the CPU utilization of an application has many advantages, such as providing " +
                "a faster and smoother user experience and extending the battery life of the device." +
                "You can use the CPU Profiler to check your application's CPU usage and thread activity " +
                "in real time while interacting with your application, as well as check the details " +
                "of the recorded method trace data, function trace data, and system trace data.";

        byte[] encrypt = AesGcmUtils.encrypt(content, key, iv);
        System.out.println(Base64.encode(encrypt));
        System.out.println(new String(AesGcmUtils.decrypt(encrypt, key, iv)));
    }

    @Test
    public void test_aes_logan_decrypt() throws Exception {

        String key = "0123456789012345";
        String iv = "0123456789012345";

        String data = "013830F6F941C450CA014716E3B541556CDA579505C3D0639B8A447A7E9E517DB2BBBFAF3D09E600D040F8B525CB10AA" +
                "52DB62A6FCD3A212460835A42495A40E22570D1593011D20CC13B377E8D213E5B20A7F013131320545FDCF508EFCD8A7C80BC1" +
                "8727566177F686D4243E876A7DFD9292EC7BAD918C4BD08A3F5B20B196832F91B88EC79E3B72F34B9292F039BA2BB7B1B380A7" +
                "11CAC095E9B42B1FA518C369F006D5768AC8A5BAF47EAB6D6D1872AED0A0EA69F69A69C56E4C9637C339B48D75A49679F50131" +
                "31320545FDCF508EFCD8A7C80BC18727566177F686D4243E876A7DFD9292EC7BAD918C4BD08A3F5B20B196832F91B88EC79E5F" +
                "73CE2AD3403388FF1911A8EBD8566326793FB004D44BED753F67A106B589E7CBFB685C36D7492547A3CBAB9F6F746E38854EF1" +
                "4BC3E7010391A43F76044940013131320545FDCF508EFCD8A7C80BC18727566177F686D4243E876A7DFD9292EC7BAD918C4BD0" +
                "8A3F5B20B196832F91B88EC79E0AE6BFFAE55F87AD14791D2AAB65AEF1AB89356C964F824649047014ED4F0FA1BEEAC3E5BCDE" +
                "9F1A4B508D08FA2EF5413432F2AC8D73B98D12674A77EBB6C8F1013131320545FDCF508EFCD8A7C80BC18727566177F686D424" +
                "3E876A7DFD9292EC7BAD918C4BD08A3F5B20B196832F91B88EC79E78BED8F823E0909962A8C8BAA1E3F1BFFC32196F8A44E36C" +
                "2C42734DE5EF4E7BA1535B03D13B438E1C06FDECC0E735518B3ABE6B729904F036407A55579981A7013131320545FDCF508EFC" +
                "D8A7C80BC18727566177F686D4243E876A7DFD9292EC7BAD918C4BD08A3F5B20B196832F91B88EC79EFA91B5D59B9288977E58" +
                "E9CB58CD4F38B38E36B45F14DDAE68E9DB72690370381BA1A57B73942CA6D610A14AD3EA8A97B9E22386B0F5CEBEBF2C315CBD" +
                "C7DD2B";
        byte[] origin = HexUtils.hexToBytes(data);

        System.out.println(origin[0]);
        byte[] len = Arrays.copyOfRange(origin, 1, 5);
        int s = BinHexOctUtils.bytesToInt(len);
        System.out.println(s);

        byte[] cipherText = Arrays.copyOfRange(origin, 5, 5 + s);
        byte[] plainText = AESUtils.decrypt(cipherText, key, iv);

        // 解压缩
        byte[] decompress = decompress(plainText);
        System.out.println(new String(decompress));

    }

    private static byte[] decompress(byte[] contentBytes) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtils.copy(new GZIPInputStream(new ByteArrayInputStream(contentBytes)), out);
        return out.toByteArray();
    }

}
