package com.hty.bm.controller.book;

import com.alibaba.fastjson.JSON;
import com.hty.bm.constant.ResponseMessage;
import com.hty.bm.entity.Book;
import com.hty.bm.entity.vo.Response;
import com.hty.bm.service.BookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/book/add")
@MultipartConfig//文件上传
public class AddBookController extends HttpServlet {
    private BookService bookService = new BookService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取数据
        String name = req.getParameter("name");
        String author = req.getParameter("author");
        Long price = Long.valueOf(req.getParameter("price"));
        String pic_path = req.getParameter("pic_path");
        String release_date = req.getParameter("release_date");
        pic_path = pic_path.substring(pic_path.lastIndexOf("/")+1);

        Book book = new Book(name,author,price,release_date,pic_path);

        Response response = null;

        try {
            bookService.add(book);
            response = new Response(ResponseMessage.SUCCESS);
        } catch (SQLException e) {
            response = new Response(ResponseMessage.ERROR);
            e.printStackTrace();
        }

        resp.getWriter().write(JSON.toJSONString(response));

    }
}

