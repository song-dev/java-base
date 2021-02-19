package com.song.utils.io2;

import com.song.utils.io.Charsets;
import javafx.util.Pair;
import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class IOTest {

    @Test
    public void test_read() {
        String path = "/Users/chensongsong/git_work/java-base/src/com/song/utils/io2/IOTest.java";
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
            System.out.println(IOUtils.toString(fileInputStream, Charsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(fileInputStream);
        }

        FileInputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        try {
            byte[] buf = new byte[4 * 1024];
            outputStream = new ByteArrayOutputStream();
            inputStream = new FileInputStream(path);
            int len;
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            System.out.println(new String(outputStream.toByteArray()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void test_write() {
        StringWriter stringWriter = null;
        try {
            stringWriter = new StringWriter();
            IOUtils.write("hello world", stringWriter);
            System.out.println(stringWriter.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(stringWriter);
        }

        String path = "/Users/chensongsong/git_work/java-base/src/com/song/utils/io2/IOTest.java";
        FileReader reader = null;
        StringWriter writer = null;
        try {
            reader = new FileReader(new File(path));
            writer = new StringWriter();
            char[] buf = new char[4 * 1024];
            int len;
            while ((len = reader.read(buf)) != -1) {
                writer.write(buf, 0, len);
            }
            System.out.println(writer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void test_copy() {
        InputStream inputStream = IOUtils.toInputStream("hello world", Charsets.UTF_8);
        StringWriter stringWriter = new StringWriter();
        try {
            IOUtils.copy(inputStream, stringWriter, "utf-8");
            System.out.println(stringWriter.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(stringWriter);
        }
    }

    @Test
    public void test_url() {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            URL url = new URL("https://www.baidu.com/");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(false);
            urlConnection.setUseCaches(false);
            inputStream = urlConnection.getInputStream();
            System.out.println(IOUtils.toString(inputStream, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    @Test
    public void test_charsets() {
        String path = "/Users/chensongsong/git_work/java-base/src/com/song/utils/io2/IOTest.java";
        FileInputStream inputStream = null;
        InputStreamReader reader = null;
        try {
            StringBuffer sb = new StringBuffer();
            inputStream = new FileInputStream(path);
            reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            int len = 0;
            char[] buf = new char[4 * 1024];
            while ((len = reader.read(buf)) != -1) {
                sb.append(buf, 0, len);
            }
            System.out.println(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        ObjectOutputStream outputStream = null;
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream("/Users/chensongsong/git_work/java-base/src/com/song/utils/io2/log.txt"));
            outputStream.writeObject(new Pair<String, String>("key", "value"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Test
    public void test_nio(){

    }

}
