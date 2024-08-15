package com.spring.jdbc;


import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DataSourceConfig {

    public static BasicDataSource createDataSource() {
        Properties properties = new Properties();
        try (InputStream input = DataSourceConfig.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException("没有找到db.properties");
            }
            // Load properties file
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(properties.getProperty("driverClassName"));
        dataSource.setUrl(properties.getProperty("url"));
        dataSource.setUsername(properties.getProperty("username"));
        dataSource.setPassword(properties.getProperty("password"));
        dataSource.setInitialSize(Integer.parseInt(properties.getProperty("initialSize")));
        dataSource.setMaxTotal(Integer.parseInt(properties.getProperty("maxTotal")));
        dataSource.setMaxIdle(Integer.parseInt(properties.getProperty("maxIdle")));
        dataSource.setMinIdle(Integer.parseInt(properties.getProperty("minIdle")));
        dataSource.setMaxWaitMillis(Long.parseLong(properties.getProperty("maxWaitMillis")));
        dataSource.setTestOnBorrow(Boolean.parseBoolean(properties.getProperty("testOnBorrow")));
        dataSource.setTestWhileIdle(Boolean.parseBoolean(properties.getProperty("testWhileIdle")));
        dataSource.setTestOnReturn(Boolean.parseBoolean(properties.getProperty("testOnReturn")));

        return dataSource;
    }
}

