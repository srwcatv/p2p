package com.catv.service;

import java.math.BigDecimal;

/**
 * 字符串转换测试
 */
public class StringTest {

    static int i = 0;

    public static void main(String[] args) {
        /*String str = "songrongwei";
        str = reversal(str);
        System.out.println(str);*/
        BigDecimal a = new BigDecimal(400);
        BigDecimal b = new BigDecimal(300);
        System.out.println(a.compareTo(b));
    }

    public static String reversal(String str) {

        if (str == null || str.length() <= 1) {
            return str;
        }
        str = reversal(str.substring(1)) + str.charAt(0);
        return str;
    }
}
