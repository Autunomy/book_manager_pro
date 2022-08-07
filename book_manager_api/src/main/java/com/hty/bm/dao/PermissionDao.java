package com.hty.bm.dao;

import com.alibaba.druid.util.StringUtils;
import com.hty.bm.entity.Book;
import com.hty.bm.entity.Permission;
import com.hty.bm.util.DataSourceUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PermissionDao {
    /***
     * 添加权限
     * @param permission
     * @return
     * @throws SQLException
     */
    public Integer insert(Permission permission) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());

        String sql = "insert into permission(tag,descp,parent_id) values(?,?,?)";
        Object[] params = {permission.getTag(),permission.getDescp(),permission.getParent_id()};
        int rows = queryRunner.execute(sql, params);

        return rows;
    }

    /***
     * 查询权限列表
     * @param currentPage
     * @param pageSize
     * @return
     * @throws SQLException
     */
    public List<Permission> selectList(String tag,String descp,Integer currentPage,Integer pageSize) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select cp.*,pp.tag parent_tag from permission cp left join permission pp on cp.parent_id = pp.id";

        if (!StringUtils.isEmpty(tag)) {
            sql += " where match(tag) against ('" + tag + "')";
        }else if(!StringUtils.isEmpty(descp)){
            sql += " where match(descp) against ('" + descp + "')";
        }
        sql += " limit ?,?";
        Object[] params = {(currentPage - 1)*pageSize,pageSize};
        List<Permission> permissions = queryRunner.query(sql, new BeanListHandler<Permission>(Permission.class), params);
        return permissions;
    }

    /***
     * 查询权限总条数
     * @return
     * @throws SQLException
     */
    public Long selectCount() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select count(*) from permission";
        Long count = (Long) queryRunner.query(sql, new ScalarHandler<>());
        return count;
    }

    /***
     * 删除一个权限
     * @param id
     * @return
     * @throws SQLException
     */
    public Integer deleteById(Integer id) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "delete from permission where id = ?";
        int rows = queryRunner.execute(sql, id);
        return rows;
    }



    /***
     * 修改一个权限
     * @param permission
     * @return
     * @throws SQLException
     */
    public int updatePermission(Permission permission) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());

        String sql = "update permission set tag=?,descp=?,parent_id=? where id = ?";
        Object[] params = {
                permission.getTag(),
                permission.getDescp(),
                permission.getParent_id(),
                permission.getId()
        };
        int rows = queryRunner.execute(sql, params);
        return rows;
    }

    public List<Permission> selectAllList() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select * from permission";
        List<Permission> permissions = queryRunner.query(sql, new BeanListHandler<Permission>(Permission.class));
        return permissions;
    }

    public List<Permission> selectAllTreeList(Integer parentId) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select * from permission where parent_id = ?";
        List<Permission> permissions = queryRunner.query(sql, new BeanListHandler<Permission>(Permission.class), parentId);
        for (Permission permission : permissions) {
            Integer id = permission.getId();
            permission.setTag(permission.getTag() + "--" + permission.getDescp());
            List<Permission> permissionsList = selectAllTreeList(id);
            permission.setChildren(permissionsList);
        }
        return permissions;
    }

    /***
     * 根据角色id查询权限列表
     * @return
     */
    public List<Permission> selectPermissionListByRid(Integer rid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select * from permission p inner join role_permission rp on p.id=rp.pid where rp.rid=?";
        List<Permission> permissions = queryRunner.query(sql, new BeanListHandler<Permission>(Permission.class),rid);
        for (Permission permission : permissions) {
            List<Permission> permissionList = this.selectAllTreeList(permission.getId());
            permission.setChildren(permissionList);
        }
        return permissions;
    }

    public List<String> selectPermissionListByUsername(String username) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select p.tag from user u\n" +
                "    inner join user_role ur on u.id=ur.uid\n" +
                "    inner join role_permission rp on ur.rid=rp.rid\n" +
                "    inner join permission p on rp.pid=p.id\n" +
                "where u.username=?;";
        List<Object[]> query = queryRunner.query(sql, new ArrayListHandler(), username);
        List<String> permissionList = new ArrayList<>();
        for (Object[] objects : query) {
            permissionList.add((String) objects[0]);
        }
        return permissionList;
    }
}
