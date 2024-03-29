package io.github.hubi0295.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class LoggerFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(LoggerFilter.class);
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(request instanceof HttpServletRequest){
            var httpReguest = (HttpServletRequest)request;
            logger.info("[doFilter]"+httpReguest.getMethod()+" "+httpReguest.getRequestURI());
        }
        chain.doFilter(request,response);
        logger.info("[doFilter] after");
    }


}
