package com.hty.bm.dao;

import com.hty.bm.entity.User;
import junit.framework.TestCase;
import org.junit.Test;

import java.sql.SQLException;

public class UserDaoTest extends TestCase {

    UserDao userDao = new UserDao();

    @Test
    public void testSelectByUserNme() throws SQLException {
        User user = userDao.selectByUserNme("admin");
        System.out.println(user);
    }
}