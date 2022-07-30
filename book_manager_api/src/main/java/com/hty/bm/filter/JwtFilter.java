package com.hty.bm.filter;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.hty.bm.constant.ResponseMessage;
import com.hty.bm.entity.vo.Response;
import com.hty.bm.util.JwtUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = request.getHeader("Authenticate");
        if(StringUtils.isEmpty(token)){
            filterChain.doFilter(request,response);
        }else{
            //更新令牌 续约token
            String username = JwtUtil.getUsernameByToken(token);
            response.addHeader("Authenticate",JwtUtil.genToken(username));
            filterChain.doFilter(request,response);
        }
    }

    @Override
    public void destroy() {

    }
}
