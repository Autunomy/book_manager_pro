package com.hty.bm.dao;

import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.SQLException;

public class UserRoleDao {
    public Integer insert(Connection connection,Integer uid,Integer rid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "insert into user_role(uid,rid) values(?,?)";
        Object[] params = {uid,rid};
        int rows = queryRunner.execute(connection, sql, params);
        return rows;
    }

    public Integer deleteByRid(Connection connection,Integer uid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();

        String sql = "delete from user_role where uid = ?";
        int rows = queryRunner.execute(connection, sql, uid);
        return rows;
    }

    public Integer update(Connection connection, Integer uid, Integer rid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "update user_role set rid = ? where uid = ?";
        int rows = queryRunner.execute(connection, sql, uid, rid);
        return rows;
    }
}
