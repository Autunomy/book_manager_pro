package com.hty.bm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    private Integer id;
    private String name;
    private String create_date;

    public Department(String name, String create_date) {
        this.name = name;
        this.create_date = create_date;
    }
}
