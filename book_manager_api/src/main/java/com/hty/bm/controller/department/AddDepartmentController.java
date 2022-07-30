package com.hty.bm.controller.department;

import com.alibaba.fastjson.JSON;
import com.hty.bm.constant.ResponseMessage;
import com.hty.bm.dao.DepartmentDao;
import com.hty.bm.entity.Department;
import com.hty.bm.entity.vo.Response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

@WebServlet("/department/add")
public class AddDepartmentController extends HttpServlet {
    private DepartmentDao departmentDao = new DepartmentDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接收客户端传递的院系信息
        String name = req.getParameter("name");
        String createDate = req.getParameter("create_date");
        Department department = new Department(name,createDate);
        Response response = null;
        try {
            departmentDao.insert(department);
            response = new Response(ResponseMessage.SUCCESS);
        } catch (SQLException e) {
            response = new Response(ResponseMessage.ERROR);
        }

        resp.setContentType("application/json");
        resp.getWriter().write(JSON.toJSONString(response));
    }
}
