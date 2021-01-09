package com.example.demo11.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringUtils {
    public static List<String> getWhitelist(String whitelist) {
        List<String> result = new ArrayList<>();
        if (hasValue(whitelist)) {
            String[] listStr = whitelist.split(";");
            result = Arrays.asList(listStr);
        }
        return result;
    }

    public static boolean hasValue(String str) {
        return (str != null && !str.isEmpty() && str.trim().length() != 0);
    }
}
