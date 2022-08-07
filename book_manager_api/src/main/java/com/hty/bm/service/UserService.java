package com.hty.bm.service;

import com.hty.bm.dao.UserDao;
import com.hty.bm.dao.UserRoleDao;
import com.hty.bm.entity.Role;
import com.hty.bm.entity.User;
import com.hty.bm.util.DataSourceUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class UserService {
    private UserDao userDao = new UserDao();
    private UserRoleDao userRoleDao = new UserRoleDao();

    public void addUser(User user,Integer rid) throws SQLException {
        DataSource dataSource = DataSourceUtil.getDataSource();
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        Integer id = userDao.insert(connection, user);
        userRoleDao.insert(connection,id,rid);
        connection.commit();
        connection.close();
    }

    public void deleteById(Integer id) throws SQLException {
        DataSource dataSource = DataSourceUtil.getDataSource();
        Connection connection = dataSource.getConnection();
        //开启事务
        connection.setAutoCommit(false);

        userDao.deleteById(connection,id);
        userRoleDao.deleteByRid(connection,id);

        connection.commit();
        connection.close();
    }

    public void updateUser(User user, Integer rid) throws SQLException {
        DataSource dataSource = DataSourceUtil.getDataSource();
        Connection connection = dataSource.getConnection();
        //开启事务
        connection.setAutoCommit(false);

        userDao.update(connection,user);
        userRoleDao.update(connection,user.getId(),rid);

        connection.commit();
        connection.close();
    }
}
