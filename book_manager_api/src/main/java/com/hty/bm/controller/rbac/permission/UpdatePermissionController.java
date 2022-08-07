package com.hty.bm.controller.rbac.permission;

import com.alibaba.fastjson.JSON;
import com.hty.bm.constant.ResponseMessage;
import com.hty.bm.dao.BookDao;
import com.hty.bm.dao.PermissionDao;
import com.hty.bm.entity.Book;
import com.hty.bm.entity.Permission;
import com.hty.bm.entity.vo.Response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/rbac/permission/update")
public class UpdatePermissionController extends HttpServlet {
    private final PermissionDao permissionDao = new PermissionDao();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Response response = null;
        Integer id = Integer.valueOf(req.getParameter("id"));
        String tag = req.getParameter("tag");
        String descp = req.getParameter("descp");
        Integer parentId = Integer.valueOf(req.getParameter("parent_id"));

        Permission permission = new Permission(id,tag,descp,parentId);

        try {
            int i = permissionDao.updatePermission(permission);
            response = new Response(ResponseMessage.SUCCESS);
        } catch (SQLException e) {
            response = new Response(ResponseMessage.ERROR);
            e.printStackTrace();
        }

        resp.getWriter().write(JSON.toJSONString(response));
    }
}
