package com.viettel.api.config;

import com.viettel.api.utils.Constants;
import io.github.bucket4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
public class RateLimitingInterceptor implements HandlerInterceptor {

    @Autowired
    private LoadConfigProp loadConfigProp;

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        final Long requestPerTime = loadConfigProp.getRequestPerTime();
        final Long time = loadConfigProp.getLimitTime();
        Bucket requestBucket = this.buckets.computeIfAbsent(Constants.BUCKET_FREE_KEY, key -> setPropBucket(requestPerTime, time));
        ConsumptionProbe probe = requestBucket.tryConsumeAndReturnRemaining(loadConfigProp.getTokensToConsume());
        if (probe.isConsumed()) {
            response.addHeader("X-Rate-Limit-Remaining", Long.toString(probe.getRemainingTokens()));
            return true;
        }

        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value()); // 429
        response.addHeader("X-Rate-Limit-Retry-After-Milliseconds",
                Long.toString(TimeUnit.NANOSECONDS.toMillis(probe.getNanosToWaitForRefill())));
        response.addHeader("Response-Message", HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase());
        return false;
    }

    private static Bucket setPropBucket(Long requestPerTime, Long time) {
        return Bucket4j.builder()
                .addLimit(Bandwidth.classic(requestPerTime, Refill.intervally(requestPerTime, Duration.ofMillis(time))))
                .build();
    }
}