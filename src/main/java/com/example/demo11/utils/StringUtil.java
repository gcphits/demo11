package com.example.demo11.utils;

import java.util.ResourceBundle;

public class StringUtil {
    private static ResourceBundle rb;
    public static String getValueFromConfig(String key) {
        rb = ResourceBundle.getBundle("config\\config");
        return rb.getString(key);
    }
}
