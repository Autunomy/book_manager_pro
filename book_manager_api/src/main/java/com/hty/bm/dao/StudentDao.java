package com.hty.bm.dao;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.hty.bm.entity.Department;
import com.hty.bm.entity.Student;
import com.hty.bm.entity.StudentDepartment;
import com.hty.bm.util.DataSourceUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class StudentDao {
    /**
     * 插入一个学生
     *
     * @param student
     * @return
     * @throws SQLException
     */
    public int insert(Student student) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "insert into student(name,age,sex,email,number,join_date,dep_id) values(?,?,?,?,?,?,?)";
        Object[] params = {
                student.getName(),
                student.getAge(),
                student.getSex(),
                student.getEmail(),
                student.getNumber(),
                student.getJoin_date(),
                student.getDep_id()
        };
        int rows = queryRunner.execute(sql, params);
        return rows;
    }

    /**
     * 分页+查询所有的学生
     *
     * @return
     * @throws SQLException
     */
    public List<StudentDepartment> getStudentList(Integer currentPage, Integer pageSize, String name, String startJoinDate, String endJoinDate) throws SQLException {
        //计算开始位置
        Integer start = (currentPage - 1) * pageSize;
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());

        //构造sql
        String baseSql = "select s.id        sid,\n" +
                "       s.name      sname,\n" +
                "       s.age,\n" +
                "       s.sex,\n" +
                "       s.email,\n" +
                "       s.number,\n" +
                "       s.join_date,\n" +
                "       s.dep_id,\n" +
                "       d.name dep_name\n" +
                "from student s\n" +
                "         inner join department d on s.dep_id = d.id\n";
        if (!StringUtils.isEmpty(name) || !StringUtils.isEmpty(startJoinDate) || !StringUtils.isEmpty(endJoinDate)) {
            baseSql += " where ";
            if (!StringUtils.isEmpty(name)) {
                baseSql += "match(s.name) against ('" + name + "')";
                if (!StringUtils.isEmpty(startJoinDate) && !StringUtils.isEmpty(endJoinDate)) {
                    baseSql += "and join_date >= '" + startJoinDate + "' and join_date <= '" + endJoinDate + "'";
                }
            } else {
                baseSql += "join_date >= '" + startJoinDate + "' and join_date <= '" + endJoinDate + "'";
            }
        }

        baseSql += " limit " + start + "," + pageSize;

        List<StudentDepartment> studentDepartmentList = queryRunner.query(baseSql, new BeanListHandler<>(StudentDepartment.class));
        return studentDepartmentList;
    }

    /**
     * 删除一个学生
     *
     * @param id 院系id
     * @return
     * @throws SQLException
     */
    public int deleteStudentById(Integer id) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "delete from student where id = ?";
        int rows = queryRunner.update(sql, id);
        return rows;
    }

    /**
     * 根据id查询学生
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public int selectStudentById(Integer id) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select * from student where id=?";
        int rows = queryRunner.update(sql, id);
        return rows;
    }


    /**
     * 获取当前表中元素的数量
     *
     * @return
     * @throws SQLException
     */
    public Long selectCount(String name, String startJoinDate, String endJoinDate) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());

        String baseSql = "select count(*) from student";
        if (!StringUtils.isEmpty(name) || !StringUtils.isEmpty(startJoinDate) || !StringUtils.isEmpty(endJoinDate)) {
            baseSql += " where ";
            if (!StringUtils.isEmpty(name)) {
                baseSql += "match(name) against ('" + name + "')";
                if (!StringUtils.isEmpty(startJoinDate) && !StringUtils.isEmpty(endJoinDate)) {
                    baseSql += "and join_date >= '" + startJoinDate + "' and join_date <= '" + endJoinDate + "'";
                }
            } else {
                baseSql += "join_date >= '" + startJoinDate + "' and join_date <= '" + endJoinDate + "'";
            }
        }

        Long count = queryRunner.query(baseSql, new ScalarHandler<>());
        return count;
    }


    /**
     * 根据id修改学生信息
     *
     * @param student
     * @return
     * @throws SQLException
     */
    public int updateStudent(Student student) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());

        String sql = "update student set name=?,age=?,sex=?,email=?,number=?,join_date=?,dep_id=? where id = ?";
        Object[] params = {
                student.getName(),
                student.getAge(),
                student.getSex(),
                student.getEmail(),
                student.getNumber(),
                student.getJoin_date(),
                student.getDep_id(),
                student.getId()
        };
        int rows = queryRunner.execute(sql, params);
        return rows;
    }
}













