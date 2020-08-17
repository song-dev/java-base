package com.song.utils;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import javafx.util.Pair;
import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.util.encoders.HexEncoder;
import org.junit.Test;

public class CryptoTest {

    @Test
    public void generate_rsa_key() throws Exception {
        Pair<String, String> pair = RSAUtils.getRSAPair();
        System.out.println("pub: " + pair.getKey());
        System.out.println("=======================");
        System.out.println("pri: " + pair.getValue());
        System.out.println("=======================");
        String pri_pem = RSAUtils.pkcs1ToPem(Base64.decode(pair.getValue()), false);
        System.out.println(pri_pem);
        System.out.println("=======================");
        String pub_pem = RSAUtils.pkcs1ToPem(Base64.decode(pair.getKey()), true);
        System.out.println(pub_pem);
        System.out.println("=======================");
    }

    @Test
    public void test_rsa_encrypt() throws Exception {

        String rsaPublicKey = "MIIBojANBgkqhkiG9w0BAQEFAAOCAY8AMIIBigKCAYEA5puXCuZ8N4rZUL/ulDQx\n" +
                "UlrFpqiyGUnOSBWHOYjl0D+lMywp+rw00j2SDXPwYfnR+3QsDb4y8fLhqQaaN0ZA\n" +
                "mHxmLN1HFkYoTzXoVkKoCuJ55vNiGJJsy6ko5YWcc3VJ/oYNCEQnMDus2HcfzD6u\n" +
                "YnlIVaiBiMrFbZYT77CPU2nNq5Gz4neXxhm5tmDPBO6VjW5PPtZ6DlIiayxq2uwU\n" +
                "J5gebvpL+23xejK+NpFqlgezDx0aQCuWucr5z2h4sgG3sf9P/Ae/yoOqPWrBhdY1\n" +
                "gY0b0Iony3LZsoyyX3Q716YH+0rctyIDn8Pa3wAdRD32YcTbU+PwmcYGBr6fwqrW\n" +
                "D7I0BbP9VlErO47zksxf37yXiyEHvdJsXw1OKHmUThoqmI0XCu1mw2Z/1irUfPo7\n" +
                "rsPLYkDEci2D9z6GRxlKs4eTaDvchC22ewlDqigK9I2xMBvRUQc240Obsx+9XGWC\n" +
                "CzeInPNWnMV2kZiebID/v4TCypCtgdiloc5DJdzoog71AgMBAAE=";

        byte[] bytes = RSAUtils.encryptWithECB("test", RSAUtils.getPublicKey(rsaPublicKey));
        String encrypted = HexUtils.bytesToHex(bytes);
        System.out.println("bytes len: " + bytes.length);
        System.out.println("encrypted len: " + encrypted.length());

    }

}
