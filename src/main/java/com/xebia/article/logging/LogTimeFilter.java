package com.xebia.article.logging;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class LogTimeFilter implements Filter {

     public static long duration;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        chain.doFilter(request, response);
         duration = System.currentTimeMillis() - startTime;
        System.out.println("Request take " + duration + " ms");
    }
}