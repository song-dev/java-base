package com.song.utils;

import com.song.utils.base64.Base64;
import org.junit.Test;

public class StringUtils {

    @Test
    public void test_len() {

        String key = "MIGJAoGBAOROSt+3cN0A1OnVA/bFVVsxzP/Aljzr9mNoDBBEKQA+zDDmZfkoL6MV\n" +
                "+ym7fIdL+7VVCicTrKaYC8yxtpltCVhqtGFjRHuM3K4kUgQMU0QZO5+ShfRGRa4t\n" +
                "QaNQcRGyOnStLTyY2kNDMoz+N0snQx6Ap9Far6nKCsslz4pG7obzAgMBAAE=";
        System.out.println(Base64.decode(key.replace("\n", "")).length);

        // 1024 pkcs8 private key len: 633, public key len: 162
        // 1024 pkcs1 private key len: 608-609, public key len: 162
        // 2048 pkcs8 private key len: 1218, public key len: 294
        // 2048 pkcs1 private key len: 608, public key len: 162

    }

    @Test
    public void read_string_len() {
        String n = "A6BFE78219DACDF3A5EABDEED3F9713A4BE1D8CCDEDFBAE1DC2B80A3D1786F2E57EAFB8238533ABC69396DAEA9DAD1D90F60F8398F4CC67790AC9B5EA9038DA078654882A8FCD12E4E942830DDC3646E1DD5D44ED4713BDA045CFBA998E3CE9A428119DD0479245DBA6E62F3994015F86B1F8C97D951133D0EEA47649869A10D";
        String d = "9766465CE9FCB06238931A406E565D6800DDDE6AA5319E0CD44AEB51D22EF743ACAFBE0C4DF7C3ACAE1D6668DFEFBDF8C6EDD472EE9956D9D891DFBFE2D54710A4C8DEF9C95AAE434EE3C6878B461E9884BBC22C880BBDCFFAD280781772FFA8FD67E6CC6A52E36638E30D20BBF874EF83D14F49CC41D9DDD29F8E9B8464FA41";
        String p = "D6B3C3031040FA8E805D5B70F9B2938E05002A27EBCFB53C39FC42BF74F249FBD9660FBF1939FAD532F0B42E1251EF8C54674BC5E2751674FCFC30801BB013E7";
        String q = "C6D2E3FF985ECEFDFC2A74741852715657A9C95E23CB48AEC17CC23B0EA01FA770CFB7151A66F585EDF28A341B5E8E0DF3E2A483EC7BDDCE8E42338CA3D244EB";
        String dp = "6A5726E3C18D2D8C9F2F9E36DAB60437776A9976A469804E9039CDA59E7AD30216AF6265384D6B5B659BAAE296D856599F047A974C8EC9CCAFF108E4AAD20F49";
        String dq = "9CDF768F8BAC7A8B817409F033E490923C96B55036D3C524457BE7506B88C190FE7C958AEC0B027BCAB3CC81B27A9780C0C63961F1AC05E55CEF34F729A6D543";
        String qp = "CA9A7CAC7A215AAE4CE047A939CC0D700FC32920FCBB4CEE94F480CDB0CD7C1AA4958750F110CDD6C6D4D04B8E25861F303986383DED4DB2AFFED2C986AFC3B3";

        String shein = "B2B28729E106D73941397EAF3F698891A3A7628E4F3DCA49DE9C635401C8B09361D581F0EC51F16B4BA251A2AB3DB8AC139AB2FF25D930E2A2220C5CD6E0689BC3C63DCB24A4BF74575415BEF6BC7F34A8EEAF0B9C0A34B2BB213E89F9C41BDDC041887AC0916CA1A2C5CBE786C9505036C7614015E02EA5E4652C5561D71C75";

        System.out.println(format_rsa_key(shein));
        System.out.println(strToHex(shein, 2));

        System.out.println("================");
        System.out.println(format_rsa_key(n));
        System.out.println("================");
        System.out.println(format_rsa_key(d));
        System.out.println("================");
        System.out.println(format_rsa_key(p));
        System.out.println("================");
        System.out.println(format_rsa_key(q));
        System.out.println("================");
        System.out.println(format_rsa_key(dp));
        System.out.println("================");
        System.out.println(format_rsa_key(dq));
        System.out.println("================");
        System.out.println(format_rsa_key(qp));
    }

    private String format_rsa_key(String key) {
        int s = key.length() / 32;
        System.out.println("line: " + s);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s; i++) {
            sb.append(key.substring(i * 32, (i + 1) * 32));
            sb.append('\n');
        }
        return sb.toString();
    }

    private String strToHex(String key, int step_size) {
        System.out.println("line: " + key.length());
        StringBuffer sb = new StringBuffer();
        int n = 0;
        int len = key.length();
        while ((len - n) > step_size) {
            sb.append("0x");
            sb.append(key.substring(n, n + step_size));
            sb.append(",");
            n += step_size;
        }
        sb.append("0x");
        sb.append(key.substring(n, len));
        return sb.toString();
    }

}
