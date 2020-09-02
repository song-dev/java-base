package com.song.utils;

import org.junit.Test;

public class GenerateDictionary {

    @Test
    public void test() {

        char[] one = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        char[] two = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        // 生成混淆字典
        for (int i = 0; i < one.length; i++) {
            System.out.println(one[i]);
        }

        for (int i = 0; i < one.length; i++) {
            for (int j = 0; j < two.length; j++) {
                System.out.println("" + one[i] + two[j]);
            }
        }

        for (int i = 0; i < one.length; i++) {
            for (int j = 0; j < two.length; j++) {
                for (int k = 0; k < two.length; k++) {
                    System.out.println("" + one[i] + two[j] + two[k]);
                }
            }
        }


    }

}
