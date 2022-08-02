package com.hty.bm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private Integer id;
    private String name;
    private Integer age;
    private String sex;
    private String email;
    private String number;
    private String join_date;
    private Integer dep_id;

    public Student(String name, Integer age, String sex, String email, String number, String join_date, Integer dep_id) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.email = email;
        this.number = number;
        this.join_date = join_date;
        this.dep_id = dep_id;
    }
}
