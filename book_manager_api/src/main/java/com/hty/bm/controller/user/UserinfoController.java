package com.hty.bm.controller.user;

import com.alibaba.fastjson.JSON;
import com.hty.bm.constant.ResponseMessage;
import com.hty.bm.entity.User;
import com.hty.bm.entity.vo.Response;
import com.hty.bm.util.JwtUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/userInfo")
public class UserinfoController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Response response = null;
        try{
            String username = JwtUtil.getUsernameByToken(req);
            User user = new User();
            user.setUsername(username);
            response = new Response(ResponseMessage.SUCCESS).data(user);
        } catch (Exception e){
            response = new Response(ResponseMessage.TOKEN_INVALID);
        }
        resp.getWriter().write(JSON.toJSONString(response));
    }
}
