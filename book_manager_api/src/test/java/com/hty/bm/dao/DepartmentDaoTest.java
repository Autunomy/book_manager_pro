package com.hty.bm.dao;

import com.hty.bm.entity.Department;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Date;

public class DepartmentDaoTest {
    @Test
    public void insert() throws SQLException {
        Department department = new Department();
        department.setName("视觉传达系");
//        department.setCreate_date(new Date(System.currentTimeMillis() - (1000L *60*60*24*365)));
        DepartmentDao departmentDao = new DepartmentDao();
        int insert = departmentDao.insert(department);
    }
}
