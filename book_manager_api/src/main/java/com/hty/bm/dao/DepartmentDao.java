package com.hty.bm.dao;

import com.alibaba.druid.util.StringUtils;
import com.hty.bm.entity.Department;
import com.hty.bm.util.DataSourceUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

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
     * 分页+查询所有的院系
     * @return
     * @throws SQLException
     */
    public List<Department> getDepartmentList(Integer currentPage, Integer pageSize, String name, String startCreateDate, String endCreateDate) throws SQLException {
        //计算开始位置
        Integer start = (currentPage - 1) * pageSize;
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());

        //构造sql
        String baseSql = "select * from department ";
        Boolean isManyCondition = false;//条件标签
        if(!StringUtils.isEmpty(name) || !StringUtils.isEmpty(startCreateDate) || !StringUtils.isEmpty(endCreateDate)){
            baseSql += " where ";
            if(!StringUtils.isEmpty(name)){
                baseSql += "match(name) against ('"+name+"')";
                if(!StringUtils.isEmpty(startCreateDate) && !StringUtils.isEmpty(endCreateDate)){
                    baseSql += "and create_date >= '"+startCreateDate+"' and create_date <= '"+endCreateDate+"'";
                }
            }else{
                baseSql += "create_date >= '"+startCreateDate+"' and create_date <= '"+endCreateDate+"'";
            }
        }

        baseSql += " limit "+start+","+pageSize;

        List<Department> departmentList = queryRunner.query(baseSql,new BeanListHandler<>(Department.class));
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


    /**
     * 获取当前表中元素的数量
     * @return
     * @throws SQLException
     */
    public Long selectCount(String name,String startCreateDate,String endCreateDate) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());

        String baseSql = "select count(*) from department";
        if(!StringUtils.isEmpty(name) || !StringUtils.isEmpty(startCreateDate) || !StringUtils.isEmpty(endCreateDate)){
            baseSql += " where ";
            if(!StringUtils.isEmpty(name)){
                baseSql += "match(name) against ('"+name+"')";
                if(!StringUtils.isEmpty(startCreateDate) && !StringUtils.isEmpty(endCreateDate)){
                    baseSql += "and create_date >= '"+startCreateDate+"' and create_date <= '"+endCreateDate+"'";
                }
            }else{
                baseSql += "create_date >= '"+startCreateDate+"' and create_date <= '"+endCreateDate+"'";
            }
        }


        Long count = queryRunner.query(baseSql, new ScalarHandler<>());
        return count;
    }


    /**
     * 根据id修改院系信息
     * @param department
     * @return
     * @throws SQLException
     */
    public int updateDepartment(Department department) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());

        String sql = "update department set name=?,create_date=? where id = ?";

        int rows = queryRunner.execute(sql, department.getName(), department.getCreate_date(),department.getId());
        return rows;
    }

    public List<Department> selectAll() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());

        String sql = "select * from department";
        List<Department> execute = queryRunner.query(sql, new BeanListHandler<Department>(Department.class));
        return execute;

    }
}
