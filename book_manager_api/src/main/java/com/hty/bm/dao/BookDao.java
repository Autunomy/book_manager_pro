package com.hty.bm.dao;

import com.alibaba.druid.util.StringUtils;
import com.hty.bm.entity.Book;
import com.hty.bm.entity.Student;
import com.hty.bm.entity.StudentDepartment;
import com.hty.bm.util.DataSourceUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BookDao {
    /**
     * 添加图书
     * 1.添加图书
     * 2.更新库存
     *
     * @param book
     * @return
     * @throws SQLException
     */
    public int insert(Connection conn, Book book) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        //添加图书
        String sql = "insert into book(name,author,price,release_date,pic_path) values(?,?,?,?,?)";
        Object[] params = {
                book.getName(),
                book.getAuthor(),
                book.getPrice(),
                book.getRelease_date(),
                book.getPic_path()
        };
        int rows = queryRunner.execute(conn, sql, params);
        return rows;
    }

    /***
     * 添加库存
     * @param stock 要加入的库存数量
     */
    public Integer addStock(Connection conn, String name,int stock) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        //添加图书
        String sql = "update book set stock = stock+" + stock + " where name = ?";
        int rows = queryRunner.execute(conn, sql, name);
        return rows;
    }

    /***
     * 根据name查询book
     * @param name
     * @return
     * @throws SQLException
     */
    public Book selectByName(String name) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select * from book where name = ?";
        Book book = queryRunner.query(sql, new BeanHandler<Book>(Book.class), name);
        return book;
    }

    /***
     * 根据id查询book
     * @param id
     * @return
     * @throws SQLException
     */
    public Book selectById(Integer id) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select * from book where id = ?";
        Book book = queryRunner.query(sql, new BeanHandler<Book>(Book.class), id);
        return book;
    }

    /***
     * 分页查询书籍
     * @param currentPage
     * @param pageSize
     * @param name
     * @param startReleaseDate
     * @param endReleaseDate
     * @return
     * @throws SQLException
     */
    public List<Book> getBookList(Integer currentPage, Integer pageSize, String name, String startReleaseDate, String endReleaseDate) throws SQLException {
        //计算开始位置
        Integer start = (currentPage - 1) * pageSize;
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());

        //构造sql
        String baseSql = "select * from book ";
        if (!StringUtils.isEmpty(name) || !StringUtils.isEmpty(startReleaseDate) || !StringUtils.isEmpty(endReleaseDate)) {
            baseSql += " where ";
            if (!StringUtils.isEmpty(name)) {
                baseSql += "match(name) against ('" + name + "')";
                if (!StringUtils.isEmpty(startReleaseDate) && !StringUtils.isEmpty(endReleaseDate)) {
                    baseSql += "and release_date >= '" + startReleaseDate + "' and release_date <= '" + endReleaseDate + "'";
                }
            } else {
                baseSql += "release_date >= '" + startReleaseDate + "' and release_date <= '" + endReleaseDate + "'";
            }
        }

        baseSql += " limit " + start + "," + pageSize;

        List<Book> bookList = queryRunner.query(baseSql, new BeanListHandler<>(Book.class));
        return bookList;
    }


    /***
     * 获取图书总数
     * @param name
     * @param startReleaseDate
     * @param endReleaseDate
     * @return
     * @throws SQLException
     */
    public Long selectCount(String name, String startReleaseDate, String endReleaseDate) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());

        String baseSql = "select count(*) from book";
        if (!StringUtils.isEmpty(name) || !StringUtils.isEmpty(startReleaseDate) || !StringUtils.isEmpty(endReleaseDate)) {
            baseSql += " where ";
            if (!StringUtils.isEmpty(name)) {
                baseSql += "match(name) against ('" + name + "')";
                if (!StringUtils.isEmpty(startReleaseDate) && !StringUtils.isEmpty(endReleaseDate)) {
                    baseSql += "and release_date >= '" + startReleaseDate + "' and release_date <= '" + endReleaseDate + "'";
                }
            } else {
                baseSql += "release_date >= '" + startReleaseDate + "' and release_date <= '" + endReleaseDate + "'";
            }
        }

        Long count = queryRunner.query(baseSql, new ScalarHandler<>());
        return count;
    }

    /***
     * 根据id删除一本书籍
     * @param id
     * @return
     * @throws SQLException
     */
    public int deleteBookById(Integer id) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "delete from book where id = ?";
        int rows = queryRunner.update(sql, id);
        return rows;
    }

    /***
     * 修改图书
     * @param book
     * @return
     * @throws SQLException
     */
    public int updateBook(Book book) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());

        String sql = "update book set name=?,author=?,price=?,release_date=?,stock=?,pic_path=? where id = ?";
        Object[] params = {
                book.getName(),
                book.getAuthor(),
                book.getPrice(),
                book.getRelease_date(),
                book.getStock(),
                book.getPic_path(),
                book.getId()
        };
        int rows = queryRunner.execute(sql, params);
        return rows;
    }

}
