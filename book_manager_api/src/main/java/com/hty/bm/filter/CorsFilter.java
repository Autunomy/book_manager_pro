package com.hty.bm.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        //允许的ip地址
        resp.addHeader("Access-Control-Allow-Origin","*");
        resp.addHeader("Access-Control-Allow-Credentials","true");
        resp.addHeader("Access-Control-Allow-Methods","*");
        resp.addHeader("Access-Control-Max-Age","true");
        //允许的跨域请求头
        resp.addHeader("Access-Control-Allow-Headers","*");
        //允许发送自定义头部
        resp.addHeader("Access-Control-Expose-Headers","*");
        filterChain.doFilter(servletRequest,resp);
    }

    @Override
    public void destroy() {

    }
}
