package com.hty.bm.service;

import com.hty.bm.dao.RoleDao;
import com.hty.bm.dao.RolePermissionDao;
import com.hty.bm.entity.Role;
import com.hty.bm.util.DataSourceUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class RoleService {
    private RoleDao roleDao = new RoleDao();
    private RolePermissionDao rolePermissionDao = new RolePermissionDao();
    /***
     * 添加角色
     * @param role 角色
     * @param permissionIds 权限列表的id
     */
    public void addRole(Role role,String[] permissionIds) throws SQLException {
        DataSource dataSource = DataSourceUtil.getDataSource();
        Connection connection = dataSource.getConnection();
        //开启事务
        connection.setAutoCommit(false);

        //添加角色信息返回添加的角色id
        Integer rid = roleDao.insert(connection, role);
        //添加角色和权限关联的信息
        for (String pid : permissionIds) {
            rolePermissionDao.insert(connection,rid,Integer.valueOf(pid));
        }
        connection.commit();
        connection.close();

    }

    /***
     * 删除角色与权限对应的关系
     * @param id
     */
    public void deleteById(Integer id) throws SQLException {
        DataSource dataSource = DataSourceUtil.getDataSource();
        Connection connection = dataSource.getConnection();
        //开启事务
        connection.setAutoCommit(false);

        roleDao.deleteById(connection,id);
        rolePermissionDao.deleteByRid(connection,id);

        connection.commit();
        connection.close();
    }

    public void updateRole(Role role,String[] permissions) throws SQLException {
        DataSource dataSource = DataSourceUtil.getDataSource();
        Connection connection = dataSource.getConnection();
        //开启事务
        connection.setAutoCommit(false);

        roleDao.update(connection,role);
        rolePermissionDao.deleteByRid(connection,role.getId());

        for (String permission : permissions) {
            rolePermissionDao.insert(connection,role.getId(),Integer.valueOf(permission));
        }

        connection.commit();
        connection.close();
    }
}
