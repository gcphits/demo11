package com.viettel.api.config;

import com.viettel.api.utils.BundleUtils;
import io.github.bucket4j.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class RateLimitingInterceptor implements HandlerInterceptor {
    private static final Long REQUEST_PER_MINUTE = Long.valueOf(BundleUtils.getConfigValue("request.per.minute"));
    private static final Long LIMIT_TIME = Long.valueOf(BundleUtils.getConfigValue("limit.time"));
    private final Bucket requestBucket = Bucket4j.builder()
            .addLimit(Bandwidth.classic(REQUEST_PER_MINUTE, Refill.intervally(REQUEST_PER_MINUTE, Duration.ofMinutes(LIMIT_TIME))))
            .build();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        ConsumptionProbe probe = requestBucket.tryConsumeAndReturnRemaining(1);
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
}