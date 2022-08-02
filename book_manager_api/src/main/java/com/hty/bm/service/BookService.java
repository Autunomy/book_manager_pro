package com.hty.bm.service;

import com.hty.bm.dao.BookDao;
import com.hty.bm.entity.Book;
import com.hty.bm.util.DataSourceUtil;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

public class BookService {
    private BookDao bookDao = new BookDao();

    /***
     * 添加图书
     * @param book
     * @throws SQLException
     */
    public void add(Book book) throws SQLException {
        DataSource dataSource = DataSourceUtil.getDataSource();
        Connection conn = dataSource.getConnection();
        conn.setAutoCommit(false);//关闭自动提交

        //判断数据库是否存在这本书
        Book bk = bookDao.selectByName(book.getName());
        if (bk == null) {//存在这本书 直接更新库存即可
            bookDao.insert(conn, book);
        }
        bookDao.addStock(conn,book.getName(),1);

        //提交事务
        conn.commit();
        conn.close();
    }
}
