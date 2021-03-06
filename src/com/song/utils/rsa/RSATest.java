package com.song.utils.rsa;

import com.song.utils.base64.Base64;
import javafx.util.Pair;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.junit.Test;

import java.io.StringWriter;

/**
 * 都为 pkcs8 格式公私钥
 */
public class RSATest {

    private static final String PRIVATE_KEY_1024 = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOROSt+3cN0A1OnV\n" +
            "A/bFVVsxzP/Aljzr9mNoDBBEKQA+zDDmZfkoL6MV+ym7fIdL+7VVCicTrKaYC8yx\n" +
            "tpltCVhqtGFjRHuM3K4kUgQMU0QZO5+ShfRGRa4tQaNQcRGyOnStLTyY2kNDMoz+\n" +
            "N0snQx6Ap9Far6nKCsslz4pG7obzAgMBAAECgYBh1l9l0eHvA606a8vgE57JjuTv\n" +
            "8OMxRll3Lh6FVgBQF5k/l2y5wovvkrronmz2OgyrH79TEa5aUiw0UzASPdTTnOWC\n" +
            "FPqIEH48ajZ3+V7Ubn6JvpqizW1NjDwtWRg8Qs2AmXmJoxd7X5V2Ekr0u9n5k7qu\n" +
            "mEgcxLTNd+K3rU1g8QJBAPTbrl5b4diMZTLYcmaC1By1XokMdNEP1L6AjdcQcXQw\n" +
            "8Bp/iVljzq0RWus0gqzX07iKe2F/ARy830/93EAWQQsCQQDuscu+hk+bVdPrHRD6\n" +
            "raoxa42xxiAjyI75kmioJrkZXnMSb4rvT9IuOif3v0Hwl04xh4QDuIUxddNvzNpM\n" +
            "jFK5AkAzjjnFpmUEOnNoZEiaKvvMW6ffBV9nUYUC44B6Av1QcKlteuMJjklLCXdy\n" +
            "RKkrCXqLxaS634n1ahM+/X2thMIHAkEAj6KD9XGDQ/lA3fGOvxp0GOscyAZuXroY\n" +
            "d0xIGC+9IOv6GPTwSRPiaZjrZiU8O9gA4MMoiMintdyuUE+YaYnjWQJAUMKivMzC\n" +
            "htawmnEtiOtwiu4dH2pCHmxBOXPGNyrmH28epPnpAkc2sbvB6CGftldYjoVbv9+k\n" +
            "ZBXsWDbOkSCyMw==";

    private static final String PUBLIC_KEY_1024 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDkTkrft3DdANTp1QP2xVVbMcz/\n" +
            "wJY86/ZjaAwQRCkAPsww5mX5KC+jFfspu3yHS/u1VQonE6ymmAvMsbaZbQlYarRh\n" +
            "Y0R7jNyuJFIEDFNEGTufkoX0RkWuLUGjUHERsjp0rS08mNpDQzKM/jdLJ0MegKfR\n" +
            "Wq+pygrLJc+KRu6G8wIDAQAB";

    private static final String PRIVATE_KEY_2048 = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCkxbDDzpg6Mmab\n" +
            "rquZsvHk5ypUetmHLMS0jqdlaV+NsG9yfUnhBNNP+ZQ8mHmNqkCTVfWcJnBWJmZo\n" +
            "B3Or3MUpxx9fbeWOm0ovYeC5bIbxXqh+PoAXTQHJadY1qyr/4BKQjIxkizH+M6/S\n" +
            "2sETXwaZ6d/CrUBnocTUinlDAzNzDslu4+AO5KhxYv24r9TslKRI9Zmtl1XZ0FX4\n" +
            "2wtX3/zBwBajzY6aVs5O+dX78Xr4oeJ81PF1gtu9Ohi1b0a8uYQaF9gTlS1l6djo\n" +
            "vveCxh5d8WeOr9eX8LbWDPCl+V+42l7c2JxrmXTK7Y8EnB3mnLZKLxA+w87/WQWF\n" +
            "O+G2GvLBAgMBAAECggEAaqq35eqGTZZ3Km2HFTbstVyY4Juu2n020hRPda0jeJwx\n" +
            "aZKGEy+GhI1zU0750aXXrAnHuFCCBKNTRCEe1rFSydroCmUgaJNLESVxz1fce8Dl\n" +
            "nuRjE7J8Z6EmVRP8AC1Ul1MFR8l8ka1wMzX+K8YmhdkLTAHQYcYtn9IM8j7OPbrW\n" +
            "BT+P1GXz6T2PvGEP6Lq3+fgRVI1zuzi0/Nw0Tsg6oQW86twnhRCTayPRaaIH4rOI\n" +
            "kAl118OSTe5CQNS1eJNMUUTeQatESnzjP8SsGjahAwtbc0T9DAONe00Pt+hgMpsA\n" +
            "lSNsSRNirJjcwe2jBvrlccFLn3W5Ab2KD7N0xMRBgQKBgQDuFMmAyA8VoUS19Uws\n" +
            "ECZqHeycjJLBSZc8Dv8Pf7zzfbGPXyq2YIgvBCtS+EwNx5na/dJXkGJtIkHTLcgM\n" +
            "LpBnLW+A4Non+d9igvkHAxeOEN2LWxVR/vZldPE2lKo2Iq+xf29vLds9nEAzZE4z\n" +
            "gDJSzzQ7v8w+Dwb8UFOmpS5KCQKBgQCxLG2J8ASTpojumsyvsV4vOvPftyC+2IhE\n" +
            "Og54MyapANwJM5C9f+8hdcw6QlfbGmUpvGBMYvCbGnQZ2VYRsngCNuGR/ZXslmFw\n" +
            "TJmEhTd7CgNZGjKaDxiyPp1OwKIzxCByyR5MPbV0pSb4xZeiOrGD50McB+ti/J1R\n" +
            "WqvKfmlw+QKBgDHTv+hn9mA8hDjgwbRnHOMQqDiLYxt432H+cXs47L65C7RJ4pKQ\n" +
            "2W+dm8Odm602nQisHp2c5jyY8tXTuw5xoF83ryJ17LJ5JTtk1eGV0BQ3i2ZjBa9H\n" +
            "gSHyu5kEvCrgT40Ccrv+COh4DyeyUnuM1PtnyjLHDWPfmUKtPRSfdXShAoGASimn\n" +
            "vIqsz8VxIjfIIG8lVq3dsbKtUVXATJbkwanfr/S9JljgaIJp7d6qW2Sz8/IrkHna\n" +
            "8X6k6rf7C2rvXT8QfxTL/Vlh7YPRS7m0tUUyQR2i4yRVNCnHk60kGfgGrZaWYpYP\n" +
            "Sai3ERmko5PKck0RS359l/k0rd3CWHCU4pFhv7kCgYEA0Ba91pU3u7dg2G8GY9pe\n" +
            "EtCImnYFRu3NZ73y0U5kqg+5IwOB+fN1grZT6zxP3/erGpymOWz/iwf5Ld/RTMcC\n" +
            "GN2WHVneWBnWK8a72dS43bEqOJsrkIssBus8+BrByE+zNQcUqFW729T5b8imzrUk\n" +
            "7gHkz1T77JUvDiYdj4rZoFc=";

    private static final String PUBLIC_KEY_2048 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApMWww86YOjJmm66rmbLx\n" +
            "5OcqVHrZhyzEtI6nZWlfjbBvcn1J4QTTT/mUPJh5japAk1X1nCZwViZmaAdzq9zF\n" +
            "KccfX23ljptKL2HguWyG8V6ofj6AF00ByWnWNasq/+ASkIyMZIsx/jOv0trBE18G\n" +
            "menfwq1AZ6HE1Ip5QwMzcw7JbuPgDuSocWL9uK/U7JSkSPWZrZdV2dBV+NsLV9/8\n" +
            "wcAWo82OmlbOTvnV+/F6+KHifNTxdYLbvToYtW9GvLmEGhfYE5UtZenY6L73gsYe\n" +
            "XfFnjq/Xl/C21gzwpflfuNpe3Nica5l0yu2PBJwd5py2Si8QPsPO/1kFhTvhthry\n" +
            "wQIDAQAB";

    private static final String PRIVATE_KEY_3072 = "MIIG/QIBADANBgkqhkiG9w0BAQEFAASCBucwggbjAgEAAoIBgQCIQybpi/gsIgas\n" +
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

    private static final String PUBLIC_KEY_3072 = "MIIBojANBgkqhkiG9w0BAQEFAAOCAY8AMIIBigKCAYEAiEMm6Yv4LCIGrH0GfZFC\n" +
            "g7ARL+NoFWnh0McIlbGHa2QrbrEBFyP2AMfwgBakpKWZKrP64t2gqIRFU08Mpef7\n" +
            "a22jbjZw2D9pImtYeQ5WYvWz7oCsGVqXN6pMQqHK8x22tyWsLy2PWLhlS6NgCe1D\n" +
            "vsyB+2j2Cy9FF9tmYrv2bg3ObfBXfs5k6V+c+zs8RcvI58fGaXTZFQ8F9EvMJzNV\n" +
            "Web72Mhyf882fjglZcTVv9BCThT4nDFuQhtsn3bz0HJB2X1yrVennORBqSedbCd3\n" +
            "s51AkA1zvpzvr9rTVvIZlme1ny4eDxMlO9BZ5hfF0TwURwj1AWpxtM/xFsFyg2kq\n" +
            "Ja2bjwO5K2YeVwk/9y4Fu5/1JXe3VaW/nkNml2ty/yHiAMYqYSslwia+UcP38ir2\n" +
            "Tv03B29pQLyv1p62zJ84J/qdVj2tpjW5xnDr9QxrzLiXLm3BEimyMqivMfAbr59U\n" +
            "D31r+vT+0vuQgn9+v9drId3n8xIgv4+7J0QWcXfFONeRAgMBAAE=";

    private static final String PRIVATE_KEY_4096 = "MIIJQwIBADANBgkqhkiG9w0BAQEFAASCCS0wggkpAgEAAoICAQCtzLGQpn9tfS99\n" +
            "CzCqAbLTzia7d4U+RZxyPdvS2m6dqMd03fAV/DK4k14PsNqTULzKpk6Bh93oBaYG\n" +
            "1r0iEdqDjJlu6Ommqcp0aWsNf7COm1gACbDFsqAONvcSjoBAUhEEldTu+HkRxoyl\n" +
            "Mb5EwIvlvlR+ghbvKpg0N/KvR5pkMGAQ0j03Hgt+CJgdiaQ8QRWLuWr91pFVMldp\n" +
            "uPgl5ZzU3YpJkpRfPosA8ZArGr2PoKSumrTtMgXn+WgmJxBsZhFzeoWlO7U2zCIT\n" +
            "ZjQaz0o5t2Cv8oBcTa6H40N9nRF8zI0XY9xQu4yzYMrKua5rTZB0TiCe25qQwOP5\n" +
            "uo/upxF3YjRW5gilY3KXp+4L+uTB91++ch4etBBQU9HnxdWo+xuffeO22HjEOKht\n" +
            "B/qHppf7VIz+yvhVOUPn184noHWPcD50P1YJwvp6KUfaqYffTdXSH6LqycNPEaXh\n" +
            "tWMpMI6LV0XYysl+p6U5FXaR+bDRsbXa5DDF+tUeS8r8TCZ9MVml4qTigSDAT/Jv\n" +
            "EOZyErWIhOk1bDUkqOkhbZTCN3JyIe3VFXMqy0asXCOUmV8WMxfiwX9bAMmJDVae\n" +
            "+oXXLkQxAYgb+0WyeqMZBi94nQa7+wcQypPpQxvAHqG4pybAXE0g8GAX0xzvMha/\n" +
            "whQ95vUn9y/yWRY5IgrCZit7ayIJvQIDAQABAoICAQCK/jTTdhRav88GtbhJfepC\n" +
            "MZAQRNmun4UqaAYxbrcgJya8c/HZ40O9ET4cVle3kM4rUy1dsgbp0/qfZYUEhjq3\n" +
            "Ee5zIJoby5Y520TeGsxcgCe0gAt7UAto0tRtZsFyry8k956/Lr/EU4GPa2S7HzHC\n" +
            "nqB8PBWTWAE5YbUYMK9siLOLQFfsiH6wYLcmiDlsZrCQeczzDHJWLFh0GJV72VG/\n" +
            "ex1pEWzPbvlD2+xsTIplHxgU+2zOdT778h40xPEBf+9c5xOSvVmDZWWrFgV9m2HF\n" +
            "Hr6Yd9vn9cgqrLoA72hY4k3D8vzzI8q2eiY0UqMG9ZUdLo4v/XhzY4/u3CDXv4zb\n" +
            "F7BAX3Pe/zK+bEJZ8cg/MfeT2qn0bIWrgfnftvk5hIsNCA4zbTAd8lHaUluD2YcA\n" +
            "1KQ+IDz7tuwE75fOFEApNYyx751pFACKtyU2sKsj2Ktqu2y0OI0j2ZzUpTb1vFVJ\n" +
            "uas53EyykENzaEHTpLkgNgLF4pf55tYEh0FpEMBdDyqOgIHRWH6pVton+r917Cdy\n" +
            "E/OFaf+CUFdkI0a4LwCWA/EuIRbIl/v7CAVEKW7OiOqBIFIFowA1hIbSmQ9Nn51u\n" +
            "vvEFRGAVyttnQhs0iOHLAK9lKPLF8Lr33qCRCPtj5+HMHgnEl37QysolhKcU2I7M\n" +
            "A9DwYKhpk/AK1uR+lzJuAQKCAQEA0ycZFtfzNILibVSRMM5/iHmLKN75haIDYkiN\n" +
            "fKJ59LwAYjEXNW7E3YDcYFXi9ECa1TPWYabv2TOA/usq7e5Y7ExldYKutuFAmnSI\n" +
            "b2t6Cj7i0Cl9RgmLchyZGN6zSHM6Lh4fUYkWaepAIhu7sxcIqffWKGTHf+vqpFrH\n" +
            "J5VotZveYoi+VbPoOzm2nfsNndus9/lJ5Jz8NVJ805lBYDTcXMOOQsLVzeLD9n19\n" +
            "RLvlTZz3O9x5OL0O+qCgspfVRvnirOef0ENm8CLL3NE01Rm48vu4AMDzMinJUS2l\n" +
            "27T3htJ2LqqfR5kIM8l/sRbfegsTaULr9ERlbXI7jjc7yZk3XQKCAQEA0racoFaM\n" +
            "OfxwPy/jX3/G3f7ZCAqJAYX/2aeqqLvYnxNv4/ECZueinoBXIqlwVL4h+qnuk/kC\n" +
            "Lwu2o+q3PcLVlNsQi5UREkO0pVBBLlwk2YsoF5SMLqwV/9pVcANNn6+YwFTvxUxy\n" +
            "pmIKER+8jU2fnWyRLpwV7lHFBsWavzL6gDKgAAo8YoVazI1uW7YwQQzl8otBkMDB\n" +
            "ZFJCZJeNURyeyz7H6nEjs+MpEPPFeYyCE3xlFxPgmqxyjCyIGWHQDI2K5SCha1Hh\n" +
            "XB0AXy6Zkdqqq7LGN18H8ghK4ls+LhFX0B4ZKfiqxQ5c/fmM0MlaSQaVLTXmIS1K\n" +
            "aeVfrRKQQ4jV4QKCAQEAvxIaOfmgMlxd2OE1oRy7exvBOXLMCIja1w0gOzAre7LE\n" +
            "Bha2EbvymR4KTYJqpOvxMAyAak1NrRZWHFdhuXx9KxbWmMdwojyi4SGidHClNrBB\n" +
            "PmzM15u7e781KrQbN+xNMX5AiN+sUJuQ/UxrRVwKkwOZ32OSw8cPxToADWm+Hti1\n" +
            "HCTxV0w9UZqgDcUoRTiMBLiojR8hen7yRefgqNYssiSjjvYP0wbonRxhRtlgXiMz\n" +
            "9g9EAnvSGilQQUvzyw5EeIIAs8zKUXKrV15YmJ12JypxDv2LFFGLwtGzzGStuGqq\n" +
            "VwA+seNg8LvduLoh76YmIpJUQe3Ceo9sJP0GNR9YYQKCAQBqLZq/MN14Wrw1Gux4\n" +
            "MusC8mhqpORk4CEMjHByljWR8zluowOS1yYauJEv5jG0ghqdNQQDjHaNPlnKP3wW\n" +
            "oxb862Zq77KqgXOF2er+cXAgLv8E4tXArvZm9p4W05NJ5DIUuLgZs2wSa1SnTFa2\n" +
            "3WaQ7SQ7WGu0q3+c6fYQYp/X+aqmWoITIx+d86aokCXAob89eO6RApBw/QJ3reaH\n" +
            "+OQLhpc2i8xx2QXYhP39QdBKAWTvPBTx2/PcrpPmNmOf77MNwmOUsK3evL5zOAgq\n" +
            "dwWJf4xiWoCmdYdGiLPMgvC87m2/uI5UvegeEqHik5PrOGXzYjWUgj39HpgXnkMT\n" +
            "xOABAoIBABqxq0jYJRa2/A5seGUy6ZolmKXJeofJBMXDeyafnf5nUXtTtVZ2qCuP\n" +
            "FIAJwGCcHuP/0BJBUVvEty7OzdoWBOMOjgw8pBzVlwelbN4dik9PnZpq5WL9erbW\n" +
            "jMh4Tb88S8SyhpipsCi640joVF1PFOgksFes8lf0R3Cihx+ju5j8ivtQmh+F1nkK\n" +
            "DrxfauAlImlcgCIHgF3vTSC3k/Pyg8RxVQL6YX/dpy2zteFCnWuwjx/0ZLdiOSyG\n" +
            "xf7tVZ0hgod2x5/hM4KCN7Gst9/fh1g4sq5X53iXHeED1vi9IEIhvgZwTAg0mkIF\n" +
            "20jeg3typS/xotTB81pbDoPTXYWYSao=";

    private static final String PUBLIC_KEY_4096 = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEArcyxkKZ/bX0vfQswqgGy\n" +
            "084mu3eFPkWccj3b0tpunajHdN3wFfwyuJNeD7Dak1C8yqZOgYfd6AWmBta9IhHa\n" +
            "g4yZbujppqnKdGlrDX+wjptYAAmwxbKgDjb3Eo6AQFIRBJXU7vh5EcaMpTG+RMCL\n" +
            "5b5UfoIW7yqYNDfyr0eaZDBgENI9Nx4LfgiYHYmkPEEVi7lq/daRVTJXabj4JeWc\n" +
            "1N2KSZKUXz6LAPGQKxq9j6Ckrpq07TIF5/loJicQbGYRc3qFpTu1NswiE2Y0Gs9K\n" +
            "Obdgr/KAXE2uh+NDfZ0RfMyNF2PcULuMs2DKyrmua02QdE4gntuakMDj+bqP7qcR\n" +
            "d2I0VuYIpWNyl6fuC/rkwfdfvnIeHrQQUFPR58XVqPsbn33jtth4xDiobQf6h6aX\n" +
            "+1SM/sr4VTlD59fOJ6B1j3A+dD9WCcL6eilH2qmH303V0h+i6snDTxGl4bVjKTCO\n" +
            "i1dF2MrJfqelORV2kfmw0bG12uQwxfrVHkvK/EwmfTFZpeKk4oEgwE/ybxDmchK1\n" +
            "iITpNWw1JKjpIW2UwjdyciHt1RVzKstGrFwjlJlfFjMX4sF/WwDJiQ1WnvqF1y5E\n" +
            "MQGIG/tFsnqjGQYveJ0Gu/sHEMqT6UMbwB6huKcmwFxNIPBgF9Mc7zIWv8IUPeb1\n" +
            "J/cv8lkWOSIKwmYre2siCb0CAwEAAQ==";

    @Test
    public void test_rsa_key() {

        System.out.println(Base64.decode(PUBLIC_KEY_1024.replace("\n", "")).length);
        System.out.println(Base64.decode(PUBLIC_KEY_2048.replace("\n", "")).length);
        System.out.println(Base64.decode(PUBLIC_KEY_3072.replace("\n", "")).length);
        System.out.println(Base64.decode(PUBLIC_KEY_4096.replace("\n", "")).length);
        System.out.println(Base64.decode(PRIVATE_KEY_1024.replace("\n", "")).length);
        System.out.println(Base64.decode(PRIVATE_KEY_2048.replace("\n", "")).length);
        System.out.println(Base64.decode(PRIVATE_KEY_3072.replace("\n", "")).length);
        System.out.println(Base64.decode(PRIVATE_KEY_4096.replace("\n", "")).length);

    }

    @Test
    public void generate_rsa_key() throws Exception {

        // 1024 pkcs8 private key len: 633-635, public key len: 162
        // 1024 pkcs1 private key len: 609, public key len: 162
        // 2048 pkcs8 private key len: 1217-1218, public key len: 294
        // 2048 pkcs1 private key len: 608, public key len: 162
        // 3072 pkcs8 private key len: 1792-1795, public key len: 422
        // 3072 pkcs1 private key len: 608, public key len: 162
        // 4096 pkcs8 private key len: 2373-2376, public key len: 550
        // 4096 pkcs1 private key len: 608, public key len: 162

        Pair<String, String> pair = RSAUtils.getRSAPair();
        System.out.println("public key: " + pair.getKey());
        System.out.println("public key len: " + Base64.decode(pair.getKey()).length);
        System.out.println("private key: " + pair.getValue());
        System.out.println("private key len: " + Base64.decode(pair.getValue()).length);
        String pri_pem = PemUtils.pkcs8ToPem(Base64.decode(pair.getValue()), false);
        System.out.println(pri_pem);
        String pub_pem = PemUtils.pkcs8ToPem(Base64.decode(pair.getKey()), true);
        System.out.println(pub_pem);

    }

    @Test
    public void test_rsa_encrypt() throws Exception {

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 62; i++) {
            sb.append("0");
        }
        System.out.println("content len: " + sb.length());

        byte[] encrypt = RSAUtils.encryptWithOAEP(sb.toString(), PUBLIC_KEY_1024.replace("\n", ""), 318);
        System.out.println("encrypt len: " + encrypt.length + ", encrypt: " + Base64.encode(encrypt));

        System.out.println(new String(RSAUtils.decryptWithOAEP(encrypt, PRIVATE_KEY_1024.replace("\n", ""), 384)));

    }

    @Test
    public void test_pem() throws Exception {
        String privateKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCz1JiHxroOf83uSGF9Ud87iaIh2SwDOden82xk+Zz6Ll3e4EECf+ZDY0kluCm90hvlrKhDMQLmc45S+WQ2aLLXCuDZUSTynffn/Eushc35gMXIHVkGyqb66NhxYUe6sikHanfsIKGnsA3wgeCOTbrKbrbDJtCdXyMYU3snwRvh5QIDAQAB";
        System.out.println(PemUtils.pkcs8ToPem(Base64.decode(privateKey),true));
    }

}
