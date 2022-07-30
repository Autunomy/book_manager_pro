package com.hty.bm.util;

import org.junit.Test;

import javax.sql.DataSource;

public class DataSourceUtilTest {
    @Test
    public void getDataSource(){
        DataSource dataSource = DataSourceUtil.getDataSource();
        System.out.println(dataSource);
    }
}
