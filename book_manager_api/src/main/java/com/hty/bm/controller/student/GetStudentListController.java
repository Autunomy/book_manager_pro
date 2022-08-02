package com.hty.bm.controller.student;

import com.alibaba.fastjson.JSON;
import com.hty.bm.constant.ResponseMessage;
import com.hty.bm.dao.DepartmentDao;
import com.hty.bm.dao.StudentDao;
import com.hty.bm.entity.Department;
import com.hty.bm.entity.PageInfo;
import com.hty.bm.entity.Student;
import com.hty.bm.entity.StudentDepartment;
import com.hty.bm.entity.vo.PageResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/student/getStudentList")
public class GetStudentListController extends HttpServlet {
    private final StudentDao studentDao = new StudentDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取分页数据
        Integer currentPage = Integer.valueOf(req.getParameter("currentPage"));
        Integer pageSize = Integer.valueOf(req.getParameter("pageSize"));

        //获取查询条件
        String name = req.getParameter("name");
        String startJoinDate = req.getParameter("startJoinDate");
        String endJoinDate = req.getParameter("endJoinDate");


        PageResponse pageResponse = null;
        try {
            List<StudentDepartment> studentDepartment = studentDao.getStudentList(currentPage, pageSize, name, startJoinDate, endJoinDate);

            //构造响应对象
            pageResponse = (PageResponse) new PageResponse(ResponseMessage.SUCCESS).data(studentDepartment);

            Long total = studentDao.selectCount(name, startJoinDate, endJoinDate);

            //构造分页信息
            PageInfo pageInfo = new PageInfo(currentPage, pageSize, total);

            pageResponse.pageInfo(pageInfo);
        } catch (SQLException e) {
            pageResponse = new PageResponse(ResponseMessage.ERROR);
            e.printStackTrace();
        }

        resp.setContentType("application/json");

        resp.getWriter().write(JSON.toJSONString(pageResponse));
    }
}
