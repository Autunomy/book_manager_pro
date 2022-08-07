package com.hty.bm.dao;

import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.SQLException;

public class RolePermissionDao {

    /***
     * 插入角色对应的权限
     * @param connection
     * @param rid
     * @param pid
     * @return
     * @throws SQLException
     */
    public Integer insert(Connection connection,Integer rid,Integer pid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "insert into role_permission(rid,pid) values (?,?)";

        Object[] params = {
                rid,pid
        };

        int rows = queryRunner.execute(connection, sql, params);

        return rows;
    }

    /***
     * 删除角色和权限的对应关系
     * @param connection
     * @param rid
     * @return
     * @throws SQLException
     */
    public Integer deleteByRid(Connection connection,Integer rid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();

        String sql = "delete from role_permission where rid = ?";
        int rows = queryRunner.execute(connection, sql, rid);
        return rows;
    }
}
