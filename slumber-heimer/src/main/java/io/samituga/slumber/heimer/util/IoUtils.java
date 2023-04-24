package io.samituga.slumber.heimer.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IoUtils {

    private static final int BUFFER_SIZE = 1024;


    public static byte[] toBytes(InputStream inputStream) throws IOException {
        return toBytes(inputStream, BUFFER_SIZE);
    }

    public static byte[] toBytes(InputStream inputStream, int bufferSize) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[bufferSize];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }
        return outputStream.toByteArray();
    }

    public static void copyTo(InputStream inputStream, OutputStream outputStream)
          throws IOException {
        copyTo(inputStream, outputStream, BUFFER_SIZE);
    }

    public static void copyTo(InputStream inputStream, OutputStream outputStream, int bufferSize)
          throws IOException {
        byte[] buffer = new byte[bufferSize];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }
    }
}
