package com.hty.bm.controller.student;

import com.alibaba.fastjson.JSON;
import com.hty.bm.constant.ResponseMessage;
import com.hty.bm.dao.DepartmentDao;
import com.hty.bm.dao.StudentDao;
import com.hty.bm.entity.Department;
import com.hty.bm.entity.Student;
import com.hty.bm.entity.vo.Response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/student/update")
public class UpdateStudentController extends HttpServlet {
    private StudentDao studentDao = new StudentDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Response response = null;

        Integer sid = Integer.valueOf(req.getParameter("id"));
        String sname = req.getParameter("name");
        Integer age = Integer.valueOf(req.getParameter("age"));
        String sex = req.getParameter("sex");
        String email = req.getParameter("email");
        String number = req.getParameter("number");
        String join_date = req.getParameter("join_date");
        Integer dep_id = Integer.valueOf(req.getParameter("dep_id"));


        Student student = new Student(sid,sname,age,sex,email,number,join_date,dep_id);
        try {
            int i = studentDao.updateStudent(student);
            response = new Response(ResponseMessage.SUCCESS);
        } catch (SQLException e) {
            response = new Response(ResponseMessage.ERROR);
            e.printStackTrace();
        }

        resp.getWriter().write(JSON.toJSONString(response));
    }
}
