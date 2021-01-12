package com.viettel.convert.config;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.grid.ProxyManager;
import org.springframework.web.servlet.HandlerInterceptor;

public class RateLimitingInterceptor implements HandlerInterceptor {
//    private final ProxyManager<String> buckets;

    public RateLimitingInterceptor(HazelcastInstance hazelcastInstance) {
//        this.buckets = Bucket4j.extension(Hazelcast.class);
    }
}
