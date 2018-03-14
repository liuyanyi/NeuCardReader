package com.mogician.cardreader;

import android.app.Activity;
import android.widget.Toast;

import java.util.regex.Pattern;

/**
 * Created by Mogician on 2018/3/14.
 */

public class Util {

    public static void aToast(String string, Activity activity) {
        Toast.makeText(activity, string, Toast.LENGTH_SHORT).show();
    }

    public static String byteToHex(byte[] bArr) {
        int i = 0;
        String[] strArr = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
        StringBuilder str = new StringBuilder();
        while (i < bArr.length) {
            int i2 = bArr[i] & 255;
            str = new StringBuilder((str + strArr[(i2 >> 4) & 15]) + strArr[i2 & 15]);
            i++;
        }
        return str.toString();
    }

    //错误提示
    public static void ErrorToast(int i, Activity activity) {
        switch (i) {
            case 1:
                Util.aToast("Authentication Failed ", activity);
                break;
            case 2:
                Util.aToast("Failed reading ", activity);
                break;
            case 3:
                Util.aToast("Failed reading 0", activity);
                break;
            case 4:
                Util.aToast("Tag reading error", activity);
                break;
            case 5:
                Util.aToast("不是Mifare卡", activity);
                break;
            case 6:
                Util.aToast("不是CPU卡", activity);
                break;
            case 7:
                Util.aToast("未找到卡", activity);
                break;
            case 8:
                Util.aToast("tag类型不对", activity);
                break;
        }
    }

    public static boolean a(String str) {
        char[] toCharArray = Pattern.compile("\\s*|\t*|\r*|\n*").matcher(str).replaceAll("").replaceAll("\\p{P}", "").trim().toCharArray();
        float length = (float) toCharArray.length;
        float f = 0.0f;
        for (char c : toCharArray) {
            if (!(Character.isLetterOrDigit(c) || a(c))) {
                f += 1.0f;
            }
        }
        return ((double) (f / length)) > 0.4d;
    }

    public static boolean a(char c) {
        Character.UnicodeBlock of = Character.UnicodeBlock.of(c);
        return of == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || of == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || of == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || of == Character.UnicodeBlock.GENERAL_PUNCTUATION || of == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || of == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }

    public static boolean d(String str) {
        return str == null || str.trim().equals("");
    }

    public static boolean g(String str) {
        return !(str == null || str.trim().equals("") || str.equalsIgnoreCase("null"));
    }

}
