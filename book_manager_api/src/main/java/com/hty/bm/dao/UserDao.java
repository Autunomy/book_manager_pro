package com.hty.bm.dao;

import com.hty.bm.entity.User;
import com.hty.bm.util.DataSourceUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class UserDao {
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户
     */
    public User selectByUserNme(String username) throws SQLException {

        DataSource dataSource = DataSourceUtil.getDataSource();
        String sql = "select * from user where username=?";
        QueryRunner queryRunner = new QueryRunner(dataSource);

        User user = (User) queryRunner.query(sql, new BeanHandler(User.class), username);

        return user;
    }
}
