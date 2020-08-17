package com.song.utils;

import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;

public class Test {

    @org.junit.Test
    public void test_key() {
        String publicKey = "MIIBojANBgkqhkiG9w0BAQEFAAOCAY8AMIIBigKCAYEA5puXCuZ8N4rZUL/ulDQxUlrFpqiyGUnOSBWHOYjl0D+lMywp+rw00j2SDXPwYfnR+3QsDb4y8fLhqQaaN0ZAmHxmLN1HFkYoTzXoVkKoCuJ55vNiGJJsy6ko5YWcc3VJ/oYNCEQnMDus2HcfzD6uYnlIVaiBiMrFbZYT77CPU2nNq5Gz4neXxhm5tmDPBO6VjW5PPtZ6DlIiayxq2uwUJ5gebvpL+23xejK+NpFqlgezDx0aQCuWucr5z2h4sgG3sf9P/Ae/yoOqPWrBhdY1gY0b0Iony3LZsoyyX3Q716YH+0rctyIDn8Pa3wAdRD32YcTbU+PwmcYGBr6fwqrWD7I0BbP9VlErO47zksxf37yXiyEHvdJsXw1OKHmUThoqmI0XCu1mw2Z/1irUfPo7rsPLYkDEci2D9z6GRxlKs4eTaDvchC22ewlDqigK9I2xMBvRUQc240Obsx+9XGWCCzeInPNWnMV2kZiebID/v4TCypCtgdiloc5DJdzoog71AgMBAAE=";
        String privateKey = "MIIBojANBgkqhkiG9w0BAQEFAAOCAY8AMIIBigKCAYEAwxii/6vzDdGv55lg3i4V17fJprQHi470MfAseC5fiSLm434ECRx1YEiG8SfSpeu1kmgWbyBHhK0Dk50G2jl0tFXQfHrai9Li8UYKfHWUa7dOG36js5xsREwsYNbokLHArX6L+IPX7D1XKar16A76kIEADK4Q10c8Mk79bbhZHhj1dgIT3NY5jVtLetnsd1Ufz3cpFBY9hx4yifNjXBSwjCq22ApA/p32603ProdVEJgb8ndD91gXV2dX3JINOKbU0m5WME+2sU99Xt0Kfs0kUofD8gw1+lWot1LmRvWGDsnu38aKFFR0CIMlXZxpbebDbjifNFLE3ZdB7nbK9TV4d995NzxUpjNbhLzQ/G8fPy31R9JGGDHNG61Dwf4pPp1ggYGTZdz8EVorsVt3Mg2Fw6felRwFO0DHIbvcrH7yk0T6kMVYBYUtMryXWv0ryQzm8jXj0MrNuWUlaFzAx/J6tE355m5stQWirqFTG1pPYohr2CzjCR5n2lgrpQgVy3IfAgMBAAE=";

        System.out.println(format_rsa_key(publicKey, 64));
        System.out.println(format_rsa_key(privateKey, 64));
    }

    private String format_rsa_key(String key, int sound) {
        int s = key.length() / sound;
        System.out.println("line: " + s);
        StringBuffer sb = new StringBuffer();
        int n = 0;
        int len = key.length();
        while ((len - n) > sound) {
            sb.append(key.substring(n, n + sound));
            sb.append('\n');
            n += sound;
        }
        sb.append(key.substring(n, len));
        sb.append('\n');

        return sb.toString();
    }

    @org.junit.Test
    public void test_aes() throws Exception {

        String IV = "0000000000000000";
        String key = "gh1vg8ya3gqr0jqf";

        System.out.println(Base64.encode("test".getBytes()));
        System.out.println(GT3Base64Util.encode("test".getBytes()));

        String content = "IcRYrh3UVYGaaYorUzlvRrpyLPo)XBqJK2Hg62(HOApAK0BwO5dI)8Ko9fOS20kbdHNYvS2YCXDi(W1d4Tuq0mmvvZUV0ueRw9L90ObLgSqavpIDILy1U5ENdD(unPRLoPLf64(tcRJ3pmj0)jLCbVI5SlNn0VAq60Hx8yA)Shy3d6AaB8k6NQQOtQbFL)6EDpRr1NF0ohN2ecpmIuE3XduKqrJdAZk9BupcB(uxwIrPXIgGj6(qyZvRLsG6uLTzW9t3E8Gv9djRpxLro837Ze0ts80XbzEExHyS0NTVQQNz(Af)NBFVNoTwqzn6841uaW73yZsi07P)VKLiAg5RHOD3SpYrj0XsV4o3qGyab(kWg45AhMG8as0yxuAJmAG)vEjU5hUw(Lzt9F9OcmfeLdXzUTQbrg9(sakphy9xhfY5I6CzsI0Zdv7HwcH43FjC(WQ5DRp4JBoHNfDQBXOMLhWu4eyS8E5LpHGAdAzUjFD)rBv1bk29j4Nlx5QoqzalVE0NdS38QqfwzFCUATtskQjp6R(5Hp7QNHiuqRwK6TYEv5ker8rXmiqYko5uqdZiS7LcCPtgbbA6CaYWTKDSVp1)7KzhMxp1hFyANRcR6(xNZa894ueJIj6inkzAg3KjEickA7xclOBNbVCa9gkMiqrvBkBrSgv6Zk(DwCYgt(reBJ1hIN3hfwcOacFl0rTRzhRixA87YfBIpo9dgUDEDPhOlp4zWXSOxnd3q6dm9Bhn9JqD0(6Glr3BiaHoXXFelB2NaFEN6PQfbdZ2MlXjZ7z2UwETMg1MAtycAiX6Ejq8bdxDov0as02)YBU1XLD9OaA6(hCNDG2(nA2UlRQE(ivfNNyCk8gvStk8K6J)W2yW3u3oyehB(hTz5(Ypgk9BRN8IQcVWNJSEiLymPXwB0kJ4JM4eO1rDhNVWTUk3VBFkgc4UjjXjqyxaamHeP7d3R4PxW0u11Bkvobr92eirF8pfR)O3tmQV6Aa87dAc714PbteINVkfF6A87HLJNFZTrmbYrCPL77PivDVHq3uV5XH1PuLAPcDSmA07B6MbwWBnK)yjU55AQyej8AyHqNIN0DeWDNGgQEliDZ1T475ube(RPgxVtHhgYFpBXGbR57g.";

        byte[] bytes = GT3Base64Util.decode(content);
        System.out.println(bytes.length);
        System.out.println(bytes.length / 16.0);

    }
}
