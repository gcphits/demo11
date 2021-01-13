package com.viettel.api.utils;

public class Constants {
    public static String HAZELCAST_INSTANCE_NAME = "application-instance-name";
    public static String BUCKET_FREE_KEY = "free-key";

    public interface InterceptorPathPattern {
        String CONVERT_JSON_ALLOW_PATH = "/convert/**";
    }
}