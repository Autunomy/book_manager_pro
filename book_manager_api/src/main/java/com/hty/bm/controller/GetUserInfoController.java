package com.hty.bm.controller;

import com.hty.bm.entity.vo.Response;
import com.hty.bm.util.JwtUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/getUserInfo")
public class GetUserInfoController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String access_token = req.getParameter("access_token");
        String username = JwtUtil.getUsernameByToken(access_token);
        resp.getWriter().write(username);
    }
}
