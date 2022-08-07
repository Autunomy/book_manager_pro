package com.hty.bm.controller.rbac.user;

import com.alibaba.fastjson.JSON;
import com.hty.bm.constant.ResponseMessage;
import com.hty.bm.dao.RoleDao;
import com.hty.bm.entity.Role;
import com.hty.bm.entity.vo.Response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/rbac/role/selectRoleByName")
public class SelectRoleByNameController extends HttpServlet {
    private RoleDao roleDao = new RoleDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        Response response = null;

        try {
            Role role = roleDao.selectByRoleName(name);
            response = new Response(ResponseMessage.SUCCESS).data(role);
        } catch (SQLException e) {
            e.printStackTrace();
            response = new Response(ResponseMessage.ERROR);
        }
        resp.getWriter().write(JSON.toJSONString(response));
    }
}
