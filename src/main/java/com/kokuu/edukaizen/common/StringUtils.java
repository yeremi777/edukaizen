package com.kokuu.edukaizen.common;

public class StringUtils {
    public static String camelToSnake(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        return value.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }
}
