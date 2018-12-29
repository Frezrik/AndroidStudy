package com.frezrik.framework.utils;

public class ConvertUtils {

    public static byte[] randomBytes(int length) {
        if (length < 0)
            return new byte[0];

        byte[] b = new byte[length];
        for (int i = 0; i < length; i++) {
            b[i] = (byte) (Math.random() * 0xff);
        }

        return b;
    }
}
