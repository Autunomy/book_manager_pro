package com.hty.bm.controller.rbac.role;

import com.alibaba.fastjson.JSON;
import com.hty.bm.constant.ResponseMessage;
import com.hty.bm.dao.PermissionDao;
import com.hty.bm.entity.Permission;
import com.hty.bm.entity.Role;
import com.hty.bm.entity.vo.Response;
import com.hty.bm.service.RoleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/rbac/role/add")
public class AddRoleController extends HttpServlet {
    private RoleService roleService = new RoleService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");//角色名称
        String descp = req.getParameter("descp");//角色描述
        String[] permissionIdLists = req.getParameterValues("permissionIdList");


        Response response = null;
        try {
            Role role = new Role(name,descp);
            roleService.addRole(role,permissionIdLists);
            response = new Response(ResponseMessage.SUCCESS);
        } catch (SQLException e) {
            e.printStackTrace();
            response = new Response(ResponseMessage.ERROR);
        }

        resp.getWriter().write(JSON.toJSONString(response));
    }
}
