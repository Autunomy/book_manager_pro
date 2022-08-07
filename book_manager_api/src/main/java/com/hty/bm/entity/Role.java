package com.hty.bm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    private Integer id;
    private String name;
    private String descp;
    private List<Permission> permissionList;//权限列表


    public Role(String name, String descp) {
        this.name = name;
        this.descp = descp;
    }
}
