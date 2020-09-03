package com.song.utils.rsa;

import com.song.utils.base64.Base64;
import javafx.util.Pair;
import org.junit.Test;

public class RSATest {

    private static final String PRIVATE_KEY = "MIIG/QIBADANBgkqhkiG9w0BAQEFAASCBucwggbjAgEAAoIBgQCIQybpi/gsIgas\n" +
            "fQZ9kUKDsBEv42gVaeHQxwiVsYdrZCtusQEXI/YAx/CAFqSkpZkqs/ri3aCohEVT\n" +
            "Twyl5/trbaNuNnDYP2kia1h5DlZi9bPugKwZWpc3qkxCocrzHba3JawvLY9YuGVL\n" +
            "o2AJ7UO+zIH7aPYLL0UX22Ziu/ZuDc5t8Fd+zmTpX5z7OzxFy8jnx8ZpdNkVDwX0\n" +
            "S8wnM1VZ5vvYyHJ/zzZ+OCVlxNW/0EJOFPicMW5CG2yfdvPQckHZfXKtV6ec5EGp\n" +
            "J51sJ3eznUCQDXO+nO+v2tNW8hmWZ7WfLh4PEyU70FnmF8XRPBRHCPUBanG0z/EW\n" +
            "wXKDaSolrZuPA7krZh5XCT/3LgW7n/Uld7dVpb+eQ2aXa3L/IeIAxiphKyXCJr5R\n" +
            "w/fyKvZO/TcHb2lAvK/WnrbMnzgn+p1WPa2mNbnGcOv1DGvMuJcubcESKbIyqK8x\n" +
            "8Buvn1QPfWv69P7S+5CCf36/12sh3efzEiC/j7snRBZxd8U415ECAwEAAQKCAYBy\n" +
            "0NKa+/vzhABHm1m6uIlpWRRCW8W6NWu3dd4/9ET9Mnx+WttmWkDRS6fU5j95FKhJ\n" +
            "GTqPqtvS4xFfLz6jFSP+03VgSrMvI88812LHLlOQMySYFQfunSzKFM8JXjcaP7FU\n" +
            "BYRehG9C/K0t3M3xz2DEBqKH61tW9U+s2IwfKe5tsWh9roSrLtJM6unlr3YMAbiZ\n" +
            "xPXkqja4zd3giXXOmiyTAlS3x77i/wEVvc2VQRQy+hLEpFsPkX8saNtQSvUKcRrW\n" +
            "aA12zFnrIwmsRcgdgZQjCI2enjBaebVr3/JS5NtZeI7VPRuTiLa3QAS8TsdL2jov\n" +
            "dI5LIJo/Ynkgqce28Altb93NJnudSs35uN7ZycCTmMZqOIIFIgA8PdnJ/JchtI+8\n" +
            "2dE3AExXomQuiZr1qzuuKXqbjd/XyhE9ZURr5uohiOjBTCbcvcQWZm1s7meHBtP4\n" +
            "pnOnm0t6tMwtl8iZCJSwZhEDOcgY1JDTSE3bOQzKH7Wwp1vgIgWjbD+mkV8gZKEC\n" +
            "gcEA47tgFV550mDF1+fRpZQnfdwtv3vFpTHscVSFcL8H5lqp47JJkzU235f78/JH\n" +
            "GsaE7WqT8nsqFTcfxhn4LffikBhuf3P8wO4cG6YIIhTL2k54401ZcSkMg4TUv0yA\n" +
            "ZwjW3q/lyhG5HZ2xY6/gdWzqI7Mq7Vr19HLXH+6o/IwmNGVPOw7svK8MsBzxVw0P\n" +
            "l3qtYPPiqDJPzin7W95i1Ro5HxPACtnXMd6Ra5e1CiT4hCzGNIl2wt/IywGYSm/N\n" +
            "oU51AoHBAJktJtbKLW6Ngwiv4R9TRCwqAdeWrqlqfnK1YCkWztQEJMRNJLVn0XOL\n" +
            "VtU/ufJM/wm9ZN7WoRq2SjZbBZ5iRGD1wzOSQRzv6c7XGh0ZeYh9TaBqv4P8zMbO\n" +
            "v6WiiQQreAgXXvbeXe3L8knGuNYQkYH7tRd9qKYTYUdLqUPyEnWYUSo1MlZv/Ajh\n" +
            "uZty1jBMZBktYEqKTrMUjmxYNrQS+0f2LV1JGVt/nupI8MFDjvtaG9rmeOe72kQS\n" +
            "Cxi4Xw45LQKBwQDKZKwnMbjpHTKYCSJMmxhiBj3uUU093Nm7exfBNTYLJwaYI8q5\n" +
            "9vBW1ZeJnghNpqNa8X6dYZLsGfq2MDxedlE4KU6DHY1D1hrLA8t0prDpyxYrRYI5\n" +
            "TPcfJ/2AKrhvWe7FKOT7qravuIeABj5hfjO1GLYNDutKnonMimpLUS9fH67D2SbT\n" +
            "JmwLVR20dRonUVMnuHj3bQd6Pdt0MXf61vxvPzlNK3/ShXEOg1zVsDfiGHzrEkSn\n" +
            "OwYUTc2QUj8ZnkUCgcA1KcCjJ8Sg7CigaTq/Po0YrvO4uamhTz7X3S+k0y+x0kZY\n" +
            "g1XMSF4ftirLFLUa7bJO1RcfaGq99KrJNso6cGy/Ik0/pvFGc9Y4XdvIYcEWqDa7\n" +
            "CJGz45fHIJbPjBv2AIhGdqYVFJSRJgnPpVMdqN53uL7filNwYKWQsyfqVygk/Fqf\n" +
            "RZtU+MUG6CEdXLwOQ1Uyj9ctx9cMvIO6Atvpn/Tqwvx3thlA8ohbVDmLOkFz55Bn\n" +
            "ZzprY32bxo9nYwZKz3kCgcAVF8oX6Yd/MTG0Yjcfz4xLoWSXnW1rLIE5cvUiseaV\n" +
            "5jDyitz1CfM41rCIt+9aCvnh55rxVc4dsqSi32VYbdLkZP4M7s0j10E87WbqGNAO\n" +
            "/9zG1yhg18SvQLPH+vrklrWXIyhNDavjc2w9BXz2xxvdTnjFp1rybNfKmvpYo6sB\n" +
            "dyl7rO2CjIAAc5HCmqIQdRGzTUTgoEgkNVgSdIy8DbvjoVfK4w450VXJ+gYJ2Jgh\n" +
            "FI0KXsU5bivyFJcHm5ufSZ0=";

    private static final String PUBLIC_KEY = "MIIBojANBgkqhkiG9w0BAQEFAAOCAY8AMIIBigKCAYEAiEMm6Yv4LCIGrH0GfZFC\n" +
            "g7ARL+NoFWnh0McIlbGHa2QrbrEBFyP2AMfwgBakpKWZKrP64t2gqIRFU08Mpef7\n" +
            "a22jbjZw2D9pImtYeQ5WYvWz7oCsGVqXN6pMQqHK8x22tyWsLy2PWLhlS6NgCe1D\n" +
            "vsyB+2j2Cy9FF9tmYrv2bg3ObfBXfs5k6V+c+zs8RcvI58fGaXTZFQ8F9EvMJzNV\n" +
            "Web72Mhyf882fjglZcTVv9BCThT4nDFuQhtsn3bz0HJB2X1yrVennORBqSedbCd3\n" +
            "s51AkA1zvpzvr9rTVvIZlme1ny4eDxMlO9BZ5hfF0TwURwj1AWpxtM/xFsFyg2kq\n" +
            "Ja2bjwO5K2YeVwk/9y4Fu5/1JXe3VaW/nkNml2ty/yHiAMYqYSslwia+UcP38ir2\n" +
            "Tv03B29pQLyv1p62zJ84J/qdVj2tpjW5xnDr9QxrzLiXLm3BEimyMqivMfAbr59U\n" +
            "D31r+vT+0vuQgn9+v9drId3n8xIgv4+7J0QWcXfFONeRAgMBAAE=";

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

        byte[] encrypt = RSAUtils.encryptWithOAEP("test", RSAUtils.getPublicKey(PUBLIC_KEY.replace("\n", "")));
        System.out.println("encrypt len: " + encrypt.length * 8 + ", encrypt: " + Base64.encode(encrypt));

        System.out.println(new String(RSAUtils.decryptWithOAEP(encrypt, RSAUtils.getPrivateKey(PRIVATE_KEY.replace("\n", "")))));

    }

}
