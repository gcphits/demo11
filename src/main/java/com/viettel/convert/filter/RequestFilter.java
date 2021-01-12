package com.viettel.convert.filter;

import com.viettel.convert.utils.BundleUtils;
import com.viettel.convert.utils.StringUtils;
import io.github.bucket4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Component
public class RequestFilter implements Filter, HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(RequestFilter.class);
    private static final String HTTP_WHITELIST = BundleUtils.getConfigValue("host.whitelist");
    private static final Long REQUEST_PER_MINUTE = Long.valueOf(BundleUtils.getConfigValue("request.per.minute"));
    private static final Long MINUTE = Long.valueOf(BundleUtils.getConfigValue("limit.minute"));
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String ipAddress = request.getRemoteAddr();
        int port = request.getRemotePort();
        List<String> ipWhitelist = StringUtils.getWhitelistHost(HTTP_WHITELIST);
        if (!ipWhitelist.contains(ipAddress)) {
            logger.error("ip address invalid");
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        Bucket requestBucket = Bucket4j.builder()
                .addLimit(Bandwidth.classic(REQUEST_PER_MINUTE, Refill.intervally(REQUEST_PER_MINUTE, Duration.ofHours(MINUTE))))
                .build();

        ConsumptionProbe probe = requestBucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            response.addHeader("X-Rate-Limit-Remaining", Long.toString(probe.getRemainingTokens()));
            return true;
        }

        System.out.println(HttpStatus.TOO_MANY_REQUESTS.value());
        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value()); // 429
        response.addHeader("X-Rate-Limit-Retry-After-Milliseconds",
                Long.toString(TimeUnit.NANOSECONDS.toMillis(probe.getNanosToWaitForRefill())));
        response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(), HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase());
        return false;
    }

//    private static Bucket getConfigSupplier() {
//        return Bucket4j.builder()
//                .addLimit(
//                        Bandwidth.classic(REQUEST_PER_MINUTE, Refill.intervally(REQUEST_PER_MINUTE, Duration.ofMinutes(MINUTE))))
//                .build();
//    }
}
