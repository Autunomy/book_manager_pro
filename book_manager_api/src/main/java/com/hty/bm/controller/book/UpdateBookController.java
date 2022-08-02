package com.hty.bm.controller.book;

import com.alibaba.fastjson.JSON;
import com.hty.bm.constant.ResponseMessage;
import com.hty.bm.dao.BookDao;
import com.hty.bm.dao.StudentDao;
import com.hty.bm.entity.Book;
import com.hty.bm.entity.Student;
import com.hty.bm.entity.vo.Response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/book/update")
public class UpdateBookController extends HttpServlet {
    private final BookDao bookDao = new BookDao();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Response response = null;

        Integer id = Integer.valueOf(req.getParameter("id"));
        String name = req.getParameter("name");
        String author = req.getParameter("author");
        Long price = Long.valueOf(req.getParameter("price"));
        String release_date = req.getParameter("release_date");
        Integer stock = Integer.valueOf(req.getParameter("stock"));
        String pic_path = req.getParameter("pic_path");
        pic_path = pic_path.substring(pic_path.lastIndexOf("/"));


        Book book = new Book(id, name, author, price, release_date, stock, pic_path);
        try {
            int i = bookDao.updateBook(book);
            response = new Response(ResponseMessage.SUCCESS);
        } catch (SQLException e) {
            response = new Response(ResponseMessage.ERROR);
            e.printStackTrace();
        }

        resp.getWriter().write(JSON.toJSONString(response));
    }
}
