package com.hty.bm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Integer id;
    private String name;
    private String author;
    private Long price;//图书价格 分为单位
    private String release_date;
    private Integer stock;
    private String pic_path;//图片路径

    public Book(String name, String author, Long price, String release_date,String pic_path) {
        this.name = name;
        this.author = author;
        this.price = price;
        this.release_date = release_date;
        this.pic_path = pic_path;
    }
}
