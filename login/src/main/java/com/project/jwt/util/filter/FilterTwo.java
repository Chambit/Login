package com.project.jwt.util.filter;

import javax.servlet.*;
import java.io.IOException;

public class FilterTwo implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        System.out.println("FilterTwo");
        chain.doFilter(request, response);
    }
}
