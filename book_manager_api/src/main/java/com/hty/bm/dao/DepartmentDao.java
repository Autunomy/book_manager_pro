package com.hty.bm.dao;

import com.hty.bm.entity.Department;
import com.hty.bm.util.DataSourceUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class DepartmentDao {
    /**
     * 插入一个院系
     * @param department
     * @return
     * @throws SQLException
     */
    public int insert(Department department) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "insert into department(name,create_date) values(?,?)";
        Object[] params = {department.getName(),department.getCreate_date()};
        int rows = queryRunner.execute(sql,params);
        return rows;
    }

    /**
     * 查询所有的院系
     * @return
     * @throws SQLException
     */
    public List<Department> getDepartmentList() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select * from department";
        List<Department> departmentList = queryRunner.query(sql,new BeanListHandler<>(Department.class));
        return departmentList;
    }

    /**
     * 删除一个院系
     * @param id 院系id
     * @return
     * @throws SQLException
     */
    public int deleteDepartmentById(Integer id) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "delete from department where id = ?";
        int rows = queryRunner.update(sql, id);
        return rows;
    }

    public Department searchDepartment(String search){
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "";
        return null;
    }

}
