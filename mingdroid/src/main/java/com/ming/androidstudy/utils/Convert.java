package com.ming.androidstudy.utils;

import android.content.Context;
import android.util.Log;

import java.nio.*;

public class Convert{
    private static final String TAG = Convert.class.getSimpleName();
    private static Convert instance;
    private Context context;

    private Convert() {
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public synchronized static Convert getInstance() {
        if (instance == null) {
            instance = new Convert();
        }

        return instance;
    }
    
    public byte[] strToBytes(String s) {
    	byte[] b = new byte[s.length()];
    	for(int i = 0; i < s.length(); i++) {
    		b[i] = (byte) (0x30 +Byte.parseByte(s.charAt(i) + ""));
    	}
    	return b;
    }
    
    public byte[] binToBytes(String s) {
    	byte[] b = new byte[s.length()];
    	for(int i = 0; i < s.length(); i=i+2) {
    		b[i] = (byte) (Byte.parseByte(s.charAt(i) + "") * 16 + Byte.parseByte(s.charAt(i + 1) + ""));
    	}
    	return b;
    }
    
    public String bcdToStr(byte[] b) throws IllegalArgumentException {
        if (b == null) {
            Log.e(TAG, "bcdToStr input arg is null");
            throw new IllegalArgumentException("bcdToStr input arg is null");
        }

        char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }

        return sb.toString();
    }

    public byte[] strToBcd(String str, EPaddingPosition paddingPosition) throws IllegalArgumentException {
        if (str == null || paddingPosition == null) {
            Log.e(TAG, "strToBcd input arg is null");
            throw new IllegalArgumentException("strToBcd input arg is null");
        }

        int len = str.length();
        int mod = len % 2;
        if (mod != 0) {
            if (paddingPosition == EPaddingPosition.PADDING_RIGHT) {
                str = str + "0";
            } else {
                str = "0" + str;
            }
            len = str.length();
        }
        byte abt[] = new byte[len];
        if (len >= 2) {
            len = len / 2;
        }
        byte bbt[] = new byte[len];
        abt = str.getBytes();
        int j = 0, k = 0;
        for (int p = 0; p < str.length() / 2; p++) {
            if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
                j = abt[2 * p] - 'a' + 0x0a;
            } else if ((abt[2 * p] >= 'A') && (abt[2 * p] <= 'Z')) {
                j = abt[2 * p] - 'A' + 0x0a;
            } else {
                j = abt[2 * p] - '0';
            }

            if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
                k = abt[2 * p + 1] - 'a' + 0x0a;
            } else if ((abt[2 * p + 1] >= 'A') && (abt[2 * p + 1] <= 'Z')) {
                k = abt[2 * p + 1] - 'A' + 0x0a;
            } else {
                k = abt[2 * p + 1] - '0';
            }

            int a = (j << 4) + k;
            byte b = (byte) a;
            bbt[p] = b;
        }
        return bbt;
    }

    public void longToByteArray(long l, byte[] to, int offset, EEndian endian) throws IllegalArgumentException {
        if (to == null || endian == null) {
            Log.e(TAG, "longToByteArray input arg is null");
            throw new IllegalArgumentException("longToByteArray input arg is null");
        }

        if (endian == EEndian.BIG_ENDIAN) {
            to[offset] = (byte) ((l >>> 56) & 0xff);
            to[offset + 1] = (byte) ((l >>> 48) & 0xff);
            to[offset + 2] = (byte) ((l >>> 40) & 0xff);
            to[offset + 3] = (byte) ((l >>> 32) & 0xff);
            to[offset + 4] = (byte) ((l >>> 24) & 0xff);
            to[offset + 5] = (byte) ((l >>> 16) & 0xff);
            to[offset + 6] = (byte) ((l >>> 8) & 0xff);
            to[offset + 7] = (byte) (l & 0xff);
        } else {
            to[offset + 7] = (byte) ((l >>> 56) & 0xff);
            to[offset + 6] = (byte) ((l >>> 48) & 0xff);
            to[offset + 5] = (byte) ((l >>> 40) & 0xff);
            to[offset + 4] = (byte) ((l >>> 32) & 0xff);
            to[offset + 3] = (byte) ((l >>> 24) & 0xff);
            to[offset + 2] = (byte) ((l >>> 16) & 0xff);
            to[offset + 1] = (byte) ((l >>> 8) & 0xff);
            to[offset] = (byte) (l & 0xff);
        }
    }

    public byte[] longToByteArray(long l, EEndian endian) throws IllegalArgumentException {
        if (endian == null) {
            Log.e(TAG, "longToByteArray input arg is null");
            throw new IllegalArgumentException("longToByteArray input arg is null");
        }

        byte[] to = new byte[8];

        if (endian == EEndian.BIG_ENDIAN) {
            to[0] = (byte) ((l >>> 56) & 0xff);
            to[1] = (byte) ((l >>> 48) & 0xff);
            to[2] = (byte) ((l >>> 40) & 0xff);
            to[3] = (byte) ((l >>> 32) & 0xff);
            to[4] = (byte) ((l >>> 24) & 0xff);
            to[5] = (byte) ((l >>> 16) & 0xff);
            to[6] = (byte) ((l >>> 8) & 0xff);
            to[7] = (byte) (l & 0xff);
        } else {
            to[7] = (byte) ((l >>> 56) & 0xff);
            to[6] = (byte) ((l >>> 48) & 0xff);
            to[5] = (byte) ((l >>> 40) & 0xff);
            to[4] = (byte) ((l >>> 32) & 0xff);
            to[3] = (byte) ((l >>> 24) & 0xff);
            to[2] = (byte) ((l >>> 16) & 0xff);
            to[1] = (byte) ((l >>> 8) & 0xff);
            to[0] = (byte) (l & 0xff);
        }

        return to;
    }

    public void intToByteArray(int i, byte[] to, int offset, EEndian endian) throws IllegalArgumentException {
        if (to == null || endian == null) {
            Log.e(TAG, "longToByteArray input arg is null");
            throw new IllegalArgumentException("longToByteArray input arg is null");
        }

        if (endian == EEndian.BIG_ENDIAN) {
            to[offset] = (byte) ((i >>> 24) & 0xff);
            to[offset + 1] = (byte) ((i >>> 16) & 0xff);
            to[offset + 2] = (byte) ((i >>> 8) & 0xff);
            to[offset + 3] = (byte) (i & 0xff);
        } else {
            to[offset] = (byte) (i & 0xff);
            to[offset + 1] = (byte) ((i >>> 8) & 0xff);
            to[offset + 2] = (byte) ((i >>> 16) & 0xff);
            to[offset + 3] = (byte) ((i >>> 24) & 0xff);
        }
    }

    public byte[] intToByteArray(int i, EEndian endian) throws IllegalArgumentException {
        if (endian == null) {
            Log.e(TAG, "intToByteArray input arg is null");
            throw new IllegalArgumentException("intToByteArray input arg is null");
        }

        byte[] to = new byte[4];

        if (endian == EEndian.BIG_ENDIAN) {
            to[0] = (byte) ((i >>> 24) & 0xff);
            to[1] = (byte) ((i >>> 16) & 0xff);
            to[2] = (byte) ((i >>> 8) & 0xff);
            to[3] = (byte) (i & 0xff);
        } else {
            to[0] = (byte) (i & 0xff);
            to[1] = (byte) ((i >>> 8) & 0xff);
            to[2] = (byte) ((i >>> 16) & 0xff);
            to[3] = (byte) ((i >>> 24) & 0xff);
        }

        return to;
    }

    public void shortToByteArray(short s, byte[] to, int offset, EEndian endian) throws IllegalArgumentException {
        if (to == null || endian == null) {
            Log.e(TAG, "shortToByteArray input arg is null");
            throw new IllegalArgumentException("shortToByteArray input arg is null");
        }

        if (endian == EEndian.BIG_ENDIAN) {
            to[offset] = (byte) ((s >>> 8) & 0xff);
            to[offset + 1] = (byte) (s & 0xff);
        } else {
            to[offset] = (byte) (s & 0xff);
            to[offset + 1] = (byte) ((s >>> 8) & 0xff);
        }
    }

    public byte[] shortToByteArray(short s, EEndian endian) throws IllegalArgumentException {
        if (endian == null) {
            Log.e(TAG, "shortToByteArray input arg is null");
            throw new IllegalArgumentException("shortToByteArray input arg is null");
        }

        byte[] to = new byte[2];

        if (endian == EEndian.BIG_ENDIAN) {
            to[0] = (byte) ((s >>> 8) & 0xff);
            to[1] = (byte) (s & 0xff);
        } else {
            to[0] = (byte) (s & 0xff);
            to[1] = (byte) ((s >>> 8) & 0xff);
        }

        return to;
    }

    public long longFromByteArray(byte[] from, int offset, EEndian endian) throws IllegalArgumentException {
        if (from == null || endian == null) {
            Log.e(TAG, "longFromByteArray input arg is null");
            throw new IllegalArgumentException("longFromByteArray input arg is null");
        }

        if (endian == EEndian.BIG_ENDIAN) {
            return ((from[offset] << 56) & 0xff00000000000000L) | ((from[offset + 1] << 48) & 0xff000000000000L)
                    | ((from[offset + 2] << 40) & 0xff0000000000L) | ((from[offset + 3] << 32) & 0xff00000000L)
                    | ((from[offset + 4] << 24) & 0xff000000) | ((from[offset + 5] << 16) & 0xff0000)
                    | ((from[offset + 6] << 8) & 0xff00) | (from[offset + 7] & 0xff);
        } else {
            return ((from[offset + 7] << 56) & 0xff00000000000000L) | ((from[offset + 6] << 48) & 0xff000000000000L)
                    | ((from[offset + 5] << 40) & 0xff0000000000L) | ((from[offset + 4] << 32) & 0xff00000000L)
                    | ((from[offset + 3] << 24) & 0xff000000) | ((from[offset + 2] << 16) & 0xff0000)
                    | ((from[offset + 1] << 8) & 0xff00) | (from[offset] & 0xff);
        }
    }

    public int intFromByteArray(byte[] from, int offset, EEndian endian) throws IllegalArgumentException {
        if (from == null || endian == null) {
            Log.e(TAG, "intFromByteArray input arg is null");
            throw new IllegalArgumentException("intFromByteArray input arg is null");
        }

        if (endian == EEndian.BIG_ENDIAN) {
            return ((from[offset] << 24) & 0xff000000) | ((from[offset + 1] << 16) & 0xff0000)
                    | ((from[offset + 2] << 8) & 0xff00) | (from[offset + 3] & 0xff);
        } else {
            return ((from[offset + 3] << 24) & 0xff000000) | ((from[offset + 2] << 16) & 0xff0000)
                    | ((from[offset + 1] << 8) & 0xff00) | (from[offset] & 0xff);
        }
    }

    public short shortFromByteArray(byte[] from, int offset, EEndian endian) throws IllegalArgumentException {
        if (from == null || endian == null) {
            Log.e(TAG, "shortFromByteArray input arg is null");
            throw new IllegalArgumentException("shortFromByteArray input arg is null");
        }

        if (endian == EEndian.BIG_ENDIAN) {
            return (short) (((from[offset] << 8) & 0xff00) | (from[offset + 1] & 0xff));
        } else {
            return (short) (((from[offset + 1] << 8) & 0xff00) | (from[offset] & 0xff));
        }
    }

    public String stringPadding(String src, char paddingChar, long expLength, EPaddingPosition paddingpos)
            throws IllegalArgumentException {
        if (src == null || paddingpos == null) {
            Log.e(TAG, "stringPadding input arg is null");
            throw new IllegalArgumentException("stringPadding input arg is null");
        }

        if (src.length() >= expLength) {
            return src;
        }

        if (paddingpos == EPaddingPosition.PADDING_RIGHT) {

            StringBuffer sb = new StringBuffer(src);
            for (int i = 0; i < expLength - src.length(); i++) {
                sb.append(paddingChar);
            }

            return sb.toString();
        } else {

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < expLength - src.length(); i++) {
                sb.append(paddingChar);
            }

            sb.append(src);
            return sb.toString();
        }
    }

    public static String byteToHex(byte bt){
        String[] strHex={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
        String resStr="";
        int low =(bt & 15);
        int high = bt>>4 & 15;
        resStr = strHex[high]+strHex[low];
        return resStr;
    }

    /**
     * padding position
     * 
     */
    public static enum EPaddingPosition {
        /**
         * padding left
         */
        PADDING_LEFT,
        /**
         * padding right
         */
        PADDING_RIGHT
    }

    /**
     * endian type
     * 
     */
    public static enum EEndian {
        /**
         * little endian
         */
        LITTLE_ENDIAN,
        /**
         * big endian
         */
        BIG_ENDIAN
    }
    
}
