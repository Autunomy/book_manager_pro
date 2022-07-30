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
import java.util.List;

@WebServlet("/department/getDepartmentList")
public class GetDepartmentListController extends HttpServlet {
    private DepartmentDao departmentDao = new DepartmentDao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Response response = null;
        try {
            List<Department> departmentList = departmentDao.getDepartmentList();
            response = new Response(ResponseMessage.SUCCESS);
            response.data(departmentList);
        } catch (SQLException e) {
            response = new Response(ResponseMessage.ERROR);
        }

        resp.setContentType("application/json");

        resp.getWriter().write(JSON.toJSONString(response));
    }
}
