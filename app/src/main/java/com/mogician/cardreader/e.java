package com.mogician.cardreader;

//TODO 工具类更名+整理
public class e {
    private static byte a(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
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

    public static final String b(byte[] bArr) {
        return a(bArr, false);
    }


    public static String d(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        for (int i = 0; i < bArr.length; i++) {
            stringBuffer.append((byte) ((bArr[i] & 240) >>> 4));
            stringBuffer.append((byte) (bArr[i] & 15));
        }
        return stringBuffer.toString().substring(0, 1).equalsIgnoreCase("0") ? stringBuffer.toString().substring(1) : stringBuffer.toString();
    }


    public static String e(byte[] bArr, int i) {
        byte[] bArr2 = new byte[7];
        a(bArr, i, bArr2, 0, 7);
        return d(bArr2);
    }

}
