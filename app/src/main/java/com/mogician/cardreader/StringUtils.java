package com.mogician.cardreader;

//TODO 工具类更名+整理
public final class StringUtils {

    public static final boolean d(String str) {
        return str == null || str.trim().equals("");
    }

    public static boolean g(String str) {
        return (str == null || str.trim().equals("") || str.equalsIgnoreCase("null")) ? false : true;
    }

}
