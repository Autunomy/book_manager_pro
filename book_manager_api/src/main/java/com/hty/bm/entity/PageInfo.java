package com.hty.bm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo {
    private Integer currentPage;//当前页面
    private Integer pageSize;//每页大小
    private Long total;//总条数
}
