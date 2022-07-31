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

@WebServlet("/department/update")
public class UpdateDepartmentController extends HttpServlet {
    private DepartmentDao departmentDao = new DepartmentDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Response response = null;

        Integer id = Integer.valueOf(req.getParameter("id"));
        String name = req.getParameter("name");
        String createDate = req.getParameter("create_date");
        Department department = new Department(id,name,createDate);

        try {
            int i = departmentDao.updateDepartment(department);
            response = new Response(ResponseMessage.SUCCESS);
        } catch (SQLException e) {
            response = new Response(ResponseMessage.ERROR);
            e.printStackTrace();
        }

        resp.getWriter().write(JSON.toJSONString(response));
    }
}
