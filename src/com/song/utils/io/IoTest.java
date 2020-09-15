package com.song.utils.io;

import org.junit.Test;

import java.io.*;

public class IoTest {

    @Test
    public void test() throws Exception {

        String s = IOUtils.toString(new FileReader("C:/git_work/java-base/src/com/song/utils/io/Charsets.java"));
        System.out.println(s.length());

        FileWriter fileWriter = new FileWriter("C:/git_work/java-base/src/com/song/utils/io/out.txt");
        IOUtils.write(s,fileWriter);
        fileWriter.flush();
        fileWriter.close();

        StringWriter stringWriter = new StringWriter();
        IOUtils.write("s",stringWriter);
        System.out.println(stringWriter.toString());

        byte[] bytes = IOUtils.toByteArray(new FileInputStream(new File("C:/git_work/java-base/src/com/song/utils/io/out.txt")));
        System.out.println(bytes.length);

    }

}
