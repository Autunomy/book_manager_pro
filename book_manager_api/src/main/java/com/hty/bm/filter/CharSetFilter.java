package com.hty.bm.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CharSetFilter implements Filter {
    private final String charset = "utf-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        filterConfig.getInitParameter(charset);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(charset);
        servletResponse.setCharacterEncoding(charset);

        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
