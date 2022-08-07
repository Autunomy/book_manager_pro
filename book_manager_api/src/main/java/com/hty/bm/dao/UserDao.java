package com.hty.bm.dao;

import com.alibaba.druid.util.StringUtils;
import com.hty.bm.entity.Role;
import com.hty.bm.entity.User;
import com.hty.bm.util.DataSourceUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserDao {
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户
     */
    public User selectByUserName(String username) throws SQLException {
        DataSource dataSource = DataSourceUtil.getDataSource();
        String sql = "select * from user where username=?";
        QueryRunner queryRunner = new QueryRunner(dataSource);

        User user = (User) queryRunner.query(sql, new BeanHandler(User.class), username);

        return user;
    }

    /***
     * 新增一个用户 返回用户id
     * @param user
     * @return
     */
    public Integer insert(Connection connection,User user) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "insert into user(username,password,descp) values(?,?,?)";
        Object[] params = {
                user.getUsername(),
                user.getPassword(),
                user.getDescp()
        };
        queryRunner.execute(connection, sql, params);
        String lastInsertIdSql = "select LAST_INSERT_ID()";
        BigInteger id = queryRunner.query(connection, lastInsertIdSql, new ScalarHandler<>());

        return id.intValue();
    }

    public List<User> selectListForPage(String username, String descp, Integer currentPage, Integer pageSize) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select u.*,r.name roleName from user u\n" +
                "    inner join user_role ur on u.id = ur.uid\n" +
                "    inner join role r on ur.rid=r.id";

        if (!StringUtils.isEmpty(username)) {
            sql += " and match(username) against ('" + username + "')";
        }else if(!StringUtils.isEmpty(descp)){
            sql += " and match(descp) against ('" + descp + "')";
        }
        sql += " limit ?,?";
        Object[] params = {(currentPage - 1)*pageSize,pageSize};
        List<User> users = queryRunner.query(sql, new BeanListHandler<User>(User.class), params);
        return users;
    }

    public Long selectCount() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select count(*) from user";
        Long count = queryRunner.query(sql,new ScalarHandler<>());
        return count;
    }

    public Integer deleteById(Connection connection,Integer id) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "delete from USER where id = ?";
        int rows = queryRunner.execute(connection,sql, id);
        return rows;
    }

    public Integer update(Connection connection, User user) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "update user set username=?,password=?,descp=? where id = ?";
        Object[] params = {user.getUsername(),user.getPassword(),user.getDescp(),user.getId()};
        int rows = queryRunner.execute(connection, sql, params);
        return rows;
    }
}
