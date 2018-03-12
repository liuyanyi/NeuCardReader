package com.mogician.cardreader;

import android.support.v4.view.InputDeviceCompat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

//TODO 工具类更名+整理
public class e {
    private static byte a(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static int a(byte[] bArr) {
        int i = 0;
        int i2 = 0;
        while (i < bArr.length) {
            i2 += (bArr[i] & 255) << ((3 - i) * 8);
            i++;
        }
        return i2;
    }

    public static int a(byte[] bArr, int i) {
        int i2 = 0;
        byte[] bArr2 = new byte[2];
        a(bArr, i, bArr2, 0, 2);
        int i3 = 0;
        while (i2 < 2) {
            i3 += (bArr2[i2] & 255) << ((1 - i2) * 8);
            i2++;
        }
        return i3;
    }

    public static String a(byte[] bArr, int i, int i2, String str) {
        try {
            String str2 = "";
            byte[] bArr2 = new byte[i2];
            a(bArr, i, bArr2, 0, i2);
            return new String(bArr2, str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final String a(byte[] bArr, boolean z) {
        StringBuffer stringBuffer = new StringBuffer(bArr.length);
        for (byte b : bArr) {
            String toHexString = Integer.toHexString(b & 255);
            if (toHexString.length() < 2) {
                stringBuffer.append(0);
            }
            stringBuffer.append(toHexString.toUpperCase());
            if (z) {
                stringBuffer.append(" ");
            }
        }
        return stringBuffer.toString();
    }

    public static void a(byte[] bArr, int i, int i2) {
        bArr[i2] = (byte) i;
    }

    public static void a(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
        if (i2 + i3 > bArr2.length) {
            throw new RuntimeException("目标字节数组所分配的长度不够");
        } else if (i + i3 > bArr.length) {
            throw new RuntimeException("源字节数组的长度与要求复制的长度不符");
        } else {
            for (int i4 = 0; i4 < i3; i4++) {
                bArr2[i2 + i4] = bArr[i + i4];
            }
        }
    }

    public static void a(byte[] bArr, long j, int i) {
        byte[] bArr2 = new byte[8];
        for (int i2 = 0; i2 < 8; i2++) {
            bArr2[i2] = (byte) ((int) (j >> ((7 - i2) * 8)));
        }
        a(bArr2, 0, bArr, i, 8);
    }

    public static void a(byte[] bArr, String str, int i, int i2, String str2) {
        try {
            a(a(str.getBytes(str2), i2, ' '), 0, bArr, i, i2);
        } catch (Exception e) {
        }
    }

    public static void a(byte[] bArr, Date date, int i) {
        a(d(new SimpleDateFormat("yyyyMMddHHmmss").format(date)), 0, bArr, i, 7);
    }

    public static void a(String[] strArr) {
        System.out.println(a(new byte[]{(byte) 39, (byte) 16}, 0));
    }
//
//    public static byte[] a(byte b) {
//        byte[] bArr = new byte[8];
//        for (int i = 7; i >= 0; i--) {
//            bArr[i] = (byte) (r3 & 1);
//            int i2 = (byte) (i2 >> 1);
//        }
//        return bArr;
//    }

    public static byte[] a(int i) {
        byte[] bArr = new byte[4];
        for (int i2 = 0; i2 < 4; i2++) {
            bArr[i2] = (byte) ((i >> ((3 - i2) * 8)) & 255);
        }
        return bArr;
    }

    public static byte[] a(long j) {
        byte[] bArr = new byte[2];
        for (int i = 0; i < 8; i++) {
            bArr[i] = (byte) ((int) ((j >>> (((bArr.length - 1) - i) * 8)) & 255));
        }
        return bArr;
    }
//
//    public static final byte[] a(Serializable serializable) throws IOException {
//        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
//        objectOutputStream.writeObject(serializable);
//        objectOutputStream.flush();
//        objectOutputStream.close();
//        return byteArrayOutputStream.toByteArray();
//    }

    public static byte[] a(String str) {
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        char[] toCharArray = str.toCharArray();
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) (a(toCharArray[i2 + 1]) | (a(toCharArray[i2]) << 4));
        }
        return bArr;
    }

    public static byte[] a(byte[] bArr, int i, char c) {
        byte[] bArr2 = new byte[i];
        a(bArr, 0, bArr2, 0, bArr.length);
        for (int length = bArr.length; length < i; length++) {
            bArr2[length] = (byte) c;
        }
        return bArr2;
    }

    public static long b(String str) {
        byte[] bArr = new byte[8];
        byte[] a = a(str);
        a(a, 0, bArr, 8 - a.length, a.length);
        return b(bArr, 0);
    }

    public static long b(byte[] bArr, int i) {
        int i2 = 0;
        long j = 0;
        byte[] bArr2 = new byte[8];
        a(bArr, i, bArr2, 0, 8);
        while (i2 < 8) {
            j |= ((long) (bArr2[i2] & 255)) << ((7 - i2) * 8);
            i2++;
        }
        return j;
    }

    public static String b(byte b) {
        return "" + ((byte) ((b >> 7) & 1)) + ((byte) ((b >> 6) & 1)) + ((byte) ((b >> 5) & 1)) + ((byte) ((b >> 4) & 1)) + ((byte) ((b >> 3) & 1)) + ((byte) ((b >> 2) & 1)) + ((byte) ((b >> 1) & 1)) + ((byte) ((b >> 0) & 1));
    }
//
//    public static final String b(Serializable serializable) throws IOException {
//        return b(a(serializable));
//    }

    public static final String b(byte[] bArr) {
        return a(bArr, false);
    }

    public static void b(byte[] bArr, int i, int i2) {
        a(a(i), 0, bArr, i2, 4);
    }

    public static byte c(byte[] bArr, int i) {
        return bArr[i];
    }

    public static final Object c(String str) throws IOException, ClassNotFoundException {
        return c(a(str));
    }

    public static final Object c(byte[] bArr) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bArr));
        Object readObject = objectInputStream.readObject();
        objectInputStream.close();
        return readObject;
    }

    public static void c(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[2];
        for (int i3 = 0; i3 < 2; i3++) {
            bArr2[i3] = (byte) ((i >> ((1 - i3) * 8)) & 255);
        }
        a(bArr2, 0, bArr, i2, 2);
    }

    public static int d(byte[] bArr, int i) {
        byte[] bArr2 = new byte[4];
        a(bArr, i, bArr2, 0, 4);
        return a(bArr2);
    }

    public static String d(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        for (int i = 0; i < bArr.length; i++) {
            stringBuffer.append((byte) ((bArr[i] & 240) >>> 4));
            stringBuffer.append((byte) (bArr[i] & 15));
        }
        return stringBuffer.toString().substring(0, 1).equalsIgnoreCase("0") ? stringBuffer.toString().substring(1) : stringBuffer.toString();
    }

    public static byte[] d(String str) {
        int length = str.length();
        if (length % 2 != 0) {
            str = "0" + str;
            length = str.length();
        }
        byte[] bArr = new byte[length];
        if (length >= 2) {
            length /= 2;
        }
        byte[] bArr2 = new byte[length];
        byte[] bytes = str.getBytes();
        for (length = 0; length < str.length() / 2; length++) {
            int i = (bytes[length * 2] < (byte) 48 || bytes[length * 2] > (byte) 57) ? (bytes[length * 2] < (byte) 97 || bytes[length * 2] > (byte) 122) ? (bytes[length * 2] - 65) + 10 : (bytes[length * 2] - 97) + 10 : bytes[length * 2] - 48;
            int i2 = (bytes[(length * 2) + 1] < (byte) 48 || bytes[(length * 2) + 1] > (byte) 57) ? (bytes[(length * 2) + 1] < (byte) 97 || bytes[(length * 2) + 1] > (byte) 122) ? (bytes[(length * 2) + 1] - 65) + 10 : (bytes[(length * 2) + 1] - 97) + 10 : bytes[(length * 2) + 1] - 48;
            bArr2[length] = (byte) ((i << 4) + i2);
        }
        return bArr2;
    }

    public static byte e(String str) {
        if (str == null) {
            return (byte) 0;
        }
        int length = str.length();
        if (length != 4 && length != 8) {
            return (byte) 0;
        }
        int parseInt = length == 8 ? str.charAt(0) == '0' ? Integer.parseInt(str, 2) : Integer.parseInt(str, 2) + InputDeviceCompat.SOURCE_ANY : Integer.parseInt(str, 2);
        return (byte) parseInt;
    }

    public static String e(byte[] bArr) {
        StringBuilder stringBuilder = new StringBuilder();
        String str = "";
        int i = 0;
        while (i < bArr.length) {
            if (i > 0 && i % 10 == 0) {
                System.out.println();
            }
            String str2 = Integer.toHexString(bArr[i]) + "  ";
            stringBuilder.append(str2);
            System.out.print(str2);
            i++;
        }
        return stringBuilder.toString();
    }

    public static String e(byte[] bArr, int i) {
        byte[] bArr2 = new byte[7];
        a(bArr, i, bArr2, 0, 7);
        return d(bArr2);
    }

    public static String f(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte append : bArr) {
            stringBuffer.append(append);
        }
        return stringBuffer.toString();
    }

    public static Date f(byte[] bArr, int i) {
        try {
            return new SimpleDateFormat("yyyyMMddHHmmss").parse(e(bArr, i));
        } catch (Exception e) {
            return null;
        }
    }
}
