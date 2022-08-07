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
import java.util.List;

@WebServlet("/rbac/permission/allTreeList")
public class ListAllTreePermessionListController extends HttpServlet {
    private PermissionDao permissionDao = new PermissionDao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Response response = null;
        try {
            List<Permission> permissions = permissionDao.selectAllTreeList(0);
            response =  new Response(ResponseMessage.SUCCESS).data(permissions);
        } catch (SQLException e) {
            e.printStackTrace();
            response = new Response(ResponseMessage.ERROR);
        }

        resp.getWriter().write(JSON.toJSONString(response));
    }
}
