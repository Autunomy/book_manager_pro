package com.hty.bm.controller.rbac.user;

import com.alibaba.fastjson.JSON;
import com.hty.bm.constant.ResponseMessage;
import com.hty.bm.dao.RoleDao;
import com.hty.bm.dao.UserDao;
import com.hty.bm.entity.PageInfo;
import com.hty.bm.entity.Role;
import com.hty.bm.entity.User;
import com.hty.bm.entity.vo.PageResponse;
import com.hty.bm.entity.vo.Response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/rbac/user/list")
public class ListUserController extends HttpServlet {
    private UserDao userDao = new UserDao();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取查询条件
        String username = req.getParameter("username");
        String descp = req.getParameter("descp");
        Integer currentPage = Integer.valueOf(req.getParameter("currentPage"));//当前页码
        Integer pageSize = Integer.valueOf(req.getParameter("pageSize"));//当前页码


        PageResponse response = null;
        try {
            List<User> users = userDao.selectListForPage(username,descp,currentPage, pageSize);
            response = (PageResponse) new PageResponse(ResponseMessage.SUCCESS).data(users);
            Long total = userDao.selectCount();
            PageInfo pageInfo = new PageInfo(currentPage,pageSize,total);
            response.pageInfo(pageInfo);
        } catch (SQLException e) {
            e.printStackTrace();
            response = new PageResponse(ResponseMessage.ERROR);
        }

        resp.getWriter().write(JSON.toJSONString(response));
    }
}

