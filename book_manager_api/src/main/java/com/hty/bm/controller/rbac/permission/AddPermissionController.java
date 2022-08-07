package com.hty.bm.controller.rbac.permission;

import com.alibaba.fastjson.JSON;
import com.hty.bm.constant.ResponseMessage;
import com.hty.bm.dao.PermissionDao;
import com.hty.bm.entity.Permission;
import com.hty.bm.entity.vo.Response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/rbac/permission/add")
public class AddPermissionController extends HttpServlet {
    private PermissionDao permissionDao = new PermissionDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tag = req.getParameter("tag");
        String descp = req.getParameter("descp");
        Integer parentId = Integer.valueOf(req.getParameter("parent_id"));//父权限id

        Permission permission = new Permission(tag, descp, parentId);
        Response response = null;
        try {
            permissionDao.insert(permission);
            response = new Response(ResponseMessage.SUCCESS);
        } catch (SQLException e) {
            e.printStackTrace();
            response = new Response(ResponseMessage.ERROR);
        }

        resp.getWriter().write(JSON.toJSONString(response));
    }
}
