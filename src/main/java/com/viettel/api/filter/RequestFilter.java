package com.viettel.api.filter;

import com.viettel.api.error.InvalidRequestException;
import com.viettel.api.utils.BundleUtils;
import com.viettel.api.utils.ErrorConstants;
import com.viettel.api.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class RequestFilter implements Filter, HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(RequestFilter.class);
    private static final String HTTP_WHITELIST = BundleUtils.getConfigValue("host.whitelist");
    private static final String PORT_WHITELIST = BundleUtils.getConfigValue("port.whitelist");

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String ipAddress = request.getRemoteAddr();
        int port = request.getRemotePort();
        List<String> ipWhitelist = StringUtils.getWhitelistHost(HTTP_WHITELIST);
        List<String> portWhitelist = StringUtils.getWhitelistHost(PORT_WHITELIST);
        if (!ipWhitelist.contains(ipAddress)) {
            logger.error("==== ip address invalid:::");
            throw new InvalidRequestException(ErrorConstants.IP_ADDRESS_INVALID_MESSAGE);
        }

//        if (!portWhitelist.contains(String.valueOf(port))) {
//            logger.error("port invalid");
//            return;
//        }

        filterChain.doFilter(request, response);
    }


}
