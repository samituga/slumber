package io.samituga.slumber.heimer.util;

public class ByteArrayUtils {

    public static byte[] mergeArrays(byte[] arr1, byte[] arr2) {

        var result = new byte[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, result, 0, arr1.length);
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
        return result;
    }
}
