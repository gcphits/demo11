package com.example.demo11.filter;

import com.example.demo11.utils.BundleUtils;
import com.example.demo11.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class RequestFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(RequestFilter.class);
    private static final String HTTP_WHITELIST = BundleUtils.getConfigValue("http.whitelist");

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String ipAddress = request.getRemoteAddr();
        List<String> ipWhitelist = StringUtils.getWhitelist(HTTP_WHITELIST);
        if (!ipWhitelist.contains(ipAddress)) {
            logger.error("ip address invalid");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
