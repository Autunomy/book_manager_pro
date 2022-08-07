package com.hty.bm.dao;

import com.alibaba.druid.util.StringUtils;
import com.hty.bm.entity.Permission;
import com.hty.bm.entity.Role;
import com.hty.bm.util.DataSourceUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RoleDao {

    private PermissionDao permissionDao = new PermissionDao();
    /***
     * 插入角色 返回的是角色的id
     * @param connection
     * @param role
     * @return
     * @throws SQLException
     */
    public Integer insert(Connection connection, Role role) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "insert into role(name,descp) values(?,?)";
        Object[] params = {
                role.getName(),role.getDescp()
        };
        int rows = queryRunner.execute(connection, sql, params);

        String lastInsertIdSql = "select LAST_INSERT_ID()";
        BigInteger id = queryRunner.query(connection, lastInsertIdSql, new ScalarHandler<>());
        return id.intValue();
    }

    /***
     * 查询角色列表用于分页
     * @param currentPage
     * @param pageSize
     * @return
     * @throws SQLException
     */
    public List<Role> selectListForPage(String name, String descp, Integer currentPage, Integer pageSize) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select * from role";

        if (!StringUtils.isEmpty(name)) {
            sql += " where match(name) against ('" + name + "')";
        }else if(!StringUtils.isEmpty(descp)){
            sql += " where match(descp) against ('" + descp + "')";
        }
        sql += " limit ?,?";
        Object[] params = {(currentPage - 1)*pageSize,pageSize};
        List<Role> roles = queryRunner.query(sql, new BeanListHandler<Role>(Role.class), params);
        return roles;
    }

    /***
     * 获取角色数量
     * @return
     * @throws SQLException
     */
    public Long selectCount() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select count(*) from role";
        Long count = queryRunner.query(sql,new ScalarHandler<>());
        return count;
    }

    /***
     * 删除角色
     * @param connection
     * @param id
     * @return
     * @throws SQLException
     */
    public Integer deleteById(Connection connection,Integer id) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "delete from role where id = ?";
        int rows = queryRunner.execute(connection,sql, id);
        return rows;
    }

    /***
     * 查询角色详情
     * @param id
     */
    public Role selectById(Integer id) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select * from role where id = ?";
        Role role = queryRunner.query(sql, new BeanHandler<>(Role.class),id);
        List<Permission> permissions = permissionDao.selectPermissionListByRid(id);
        role.setPermissionList(permissions);
        return role;
    }

    public Integer update(Connection connection,Role role) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "update role set name=?,descp=? where id = ?";
        Object[] params = {"ROLE_"+role.getName(),role.getDescp(),role.getId()};
        int rows = queryRunner.execute(connection, sql, params);
        return rows;
    }

    public List<Role> selectRoleList() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select * from role";
        List<Role> roleList = queryRunner.query(sql, new BeanListHandler<Role>(Role.class));
        return roleList;
    }

    public Role selectByRoleName(String name) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select * from role where name=?";
        Role role = queryRunner.query(sql, new BeanHandler<>(Role.class), name);
        return role;
    }
}
