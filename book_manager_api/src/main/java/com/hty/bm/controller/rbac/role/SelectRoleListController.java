package com.hty.bm.controller.rbac.role;

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
import java.util.List;

@WebServlet("/rbac/role/selectRoleList")
public class SelectRoleListController extends HttpServlet {
    private RoleDao roleDao = new RoleDao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Response response = null;
        try {
            List<Role> roleList = roleDao.selectRoleList();
            response = new Response(ResponseMessage.SUCCESS).data(roleList);
        } catch (SQLException e) {
            e.printStackTrace();
            response = new Response(ResponseMessage.ERROR);
        }

        resp.getWriter().write(JSON.toJSONString(response));
    }
}
