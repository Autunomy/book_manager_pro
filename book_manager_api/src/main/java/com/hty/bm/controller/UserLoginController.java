package com.hty.bm.controller;

import com.alibaba.fastjson.JSON;
import com.hty.bm.constant.ResponseMessage;
import com.hty.bm.dao.UserDao;
import com.hty.bm.entity.User;
import com.hty.bm.entity.vo.Response;
import com.hty.bm.util.JwtUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/user/login")
public class UserLoginController extends HttpServlet {
    private UserDao userDao = new UserDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接收客户端传递的用户名和密码
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Response response = null;

        try {
            User user = userDao.selectByUserNme(username);
            if (user == null) {
                response = new Response(ResponseMessage.USER_NOT_FOUND);
            } else {
                if (user.getPassword().equals(password)) {
                    response = new Response(ResponseMessage.SUCCESS);
                    String token = JwtUtil.genToken(username);
                    Map<String, String> map = new HashMap<>();
                    map.put("access_token", token);
                    response.data(map);
                } else {
                    response = new Response(ResponseMessage.PASSWORD_NOT_MATCHED);
                }
            }
        } catch (SQLException e) {
            response = new Response(ResponseMessage.ERROR);
        }

        resp.setContentType("application/json");
        resp.getWriter().write(JSON.toJSONString(response));
    }
}
