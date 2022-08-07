package com.hty.bm.controller.rbac.role;

import com.alibaba.fastjson.JSON;
import com.hty.bm.constant.ResponseMessage;
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

@WebServlet("/rbac/role/update")
public class UpdateRoleController extends HttpServlet {
    private RoleService roleService = new RoleService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Response response = null;
        Integer id = Integer.valueOf(req.getParameter("id"));
        String name = req.getParameter("name");
        String descp = req.getParameter("descp");
        String[] permissionListIds = req.getParameterValues("permissionListIds");
        Role role = new Role(name,descp);
        role.setId(id);
        try {
            roleService.updateRole(role,permissionListIds);
            response = new Response(ResponseMessage.SUCCESS);
        } catch (SQLException e) {
            e.printStackTrace();
            response = new Response(ResponseMessage.ERROR);
        }

        resp.getWriter().write(JSON.toJSONString(response));
    }
}
