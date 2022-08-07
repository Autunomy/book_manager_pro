package com.hty.bm.controller.rbac.user;

import com.alibaba.fastjson.JSON;
import com.hty.bm.constant.ResponseMessage;
import com.hty.bm.entity.User;
import com.hty.bm.entity.vo.Response;
import com.hty.bm.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/rbac/user/add")
public class AddUserController extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Response response = null;
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String descp = req.getParameter("descp");
        Integer rid = Integer.valueOf(req.getParameter("rid"));
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setDescp(descp);
        try {
            userService.addUser(user,rid);
            response = new Response(ResponseMessage.SUCCESS);
        } catch (SQLException e) {
            e.printStackTrace();
            response = new Response(ResponseMessage.ERROR);
        }

        resp.getWriter().write(JSON.toJSONString(response));
    }
}
