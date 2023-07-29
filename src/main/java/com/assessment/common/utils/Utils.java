package com.assessment.common.utils;

public class Utils {
    public static boolean isOk(String str) {
        return !(str == null || str.isEmpty());
    }
    public static boolean isOk(Object value) {
        return !(value == null);
    }
}
