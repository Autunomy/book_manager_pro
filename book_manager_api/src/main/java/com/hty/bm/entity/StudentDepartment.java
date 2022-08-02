package com.hty.bm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDepartment{

    private Integer sid;
    private String sname;
    private Integer age;
    private String sex;
    private String email;
    private String number;
    private String join_date;
    private Integer dep_id;
    private String dep_name;
}
