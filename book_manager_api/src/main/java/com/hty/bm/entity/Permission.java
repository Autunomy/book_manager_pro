package com.hty.bm.entity;

import com.hty.bm.dao.PermissionDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    private Integer id;
    private String tag;
    private String descp;
    private Integer parent_id;
    private String parent_tag;//父权限的标识
    private List<Permission> children;

    public Permission(String tag, String descp,Integer parent_id) {
        this.tag = tag;
        this.descp = descp;
        this.parent_id = parent_id;
    }
    public Permission(Integer id,String tag, String descp,Integer parent_id) {
        this.id = id;
        this.tag = tag;
        this.descp = descp;
        this.parent_id = parent_id;
    }
}
