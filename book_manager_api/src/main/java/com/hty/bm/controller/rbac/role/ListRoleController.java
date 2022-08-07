package com.hty.bm.controller.rbac.role;

import com.alibaba.fastjson.JSON;
import com.hty.bm.constant.ResponseMessage;
import com.hty.bm.dao.PermissionDao;
import com.hty.bm.dao.RoleDao;
import com.hty.bm.entity.PageInfo;
import com.hty.bm.entity.Permission;
import com.hty.bm.entity.Role;
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

@WebServlet("/rbac/role/list")
public class ListRoleController extends HttpServlet {
    private RoleDao roleDao = new RoleDao();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取查询条件
        String name = req.getParameter("name");
        String descp = req.getParameter("descp");

        Integer currentPage = Integer.valueOf(req.getParameter("currentPage"));//当前页码
        Integer pageSize = Integer.valueOf(req.getParameter("pageSize"));//当前页码


        PageResponse response = null;
        try {
            List<Role> roles = roleDao.selectListForPage(name,descp,currentPage, pageSize);
            response = (PageResponse) new PageResponse(ResponseMessage.SUCCESS).data(roles);
            Long total = roleDao.selectCount();
            PageInfo pageInfo = new PageInfo(currentPage,pageSize,total);
            response.pageInfo(pageInfo);
        } catch (SQLException e) {
            e.printStackTrace();
            response = (PageResponse) new Response(ResponseMessage.ERROR);
        }

        resp.getWriter().write(JSON.toJSONString(response));
    }
}

