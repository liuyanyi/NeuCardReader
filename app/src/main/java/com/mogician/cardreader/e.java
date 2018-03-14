package com.mogician.cardreader;

//TODO 工具类更名+整理
public class e {
    private static byte a(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static final String a(byte[] bArr, boolean z) {
        StringBuilder stringBuilder = new StringBuilder(bArr.length);
        for (byte b : bArr) {
            String toHexString = Integer.toHexString(b & 255);
            if (toHexString.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(toHexString.toUpperCase());
            if (z) {
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }

    public static final String b(byte[] bArr) {
        return a(bArr, false);
    }


    public static String d(byte[] bArr) {
        StringBuilder stringBuilder = new StringBuilder(bArr.length * 2);
        for (byte aBArr : bArr) {
            stringBuilder.append((byte) ((aBArr & 240) >>> 4));
            stringBuilder.append((byte) (aBArr & 15));
        }
        return stringBuilder.toString().substring(0, 1).equalsIgnoreCase("0") ? stringBuilder.toString().substring(1) : stringBuilder.toString();
    }


    public static String e1(byte[] bArr, int i) {
        byte[] bArr2 = new byte[7];
        g.a(bArr, i, bArr2, 0, 7);
        return d(bArr2);
    }

}
