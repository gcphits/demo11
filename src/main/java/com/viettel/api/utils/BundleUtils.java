package com.viettel.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ResourceBundle;

public class BundleUtils {
    private static final Logger logger = LoggerFactory.getLogger(BundleUtils.class);
    private static ResourceBundle rb;
    private static final String CONFIG = "config/config";

    public static String getConfigValue(String key) {
        String result = null;
        try {
            rb = ResourceBundle.getBundle(CONFIG);
            result = rb.getString(key);
        } catch (Exception ex) {
            logger.error("==== error get value from config:::", ex);
        }
        return result;
    }
}
