package com.hty.bm.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

public class DataSourceUtil {
    private static DataSource dataSource;

    //单例饿汉模式直接创建数据源对象
    static{
        Properties prop = new Properties();
        try {
            prop.load(DataSourceUtil.class.getClassLoader().getResourceAsStream("db.properties"));
            dataSource = DruidDataSourceFactory.createDataSource(prop);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //构造方法私有化
    private DataSourceUtil(){}

    public static DataSource getDataSource(){
        return dataSource;
    }
}
