package com.spring.jdbc;

import com.spring.core.factory.config.InitializingBean;
import org.apache.commons.dbcp2.BasicDataSource;



import java.sql.Connection;
import java.sql.SQLException;


public class TransactionManager implements InitializingBean{

    private static BasicDataSource dataSource;
    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();


    public TransactionManager(BasicDataSource dataSource) {
        TransactionManager.dataSource = dataSource;
    }

    public TransactionManager() {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        dataSource = DataSourceConfig.createDataSource();

    }

    // 开始事务
    public void beginTransaction() throws SQLException {
        // Start a new transaction
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        System.out.println("begin transaction");
        connectionHolder.set(connection);
    }

    // 提交事务
    public void commit() throws SQLException {
        Connection connection = connectionHolder.get();
        if (connection != null) {
            System.out.println(connection);
            connection.commit();
            connection.close();
        }
    }

    // 回滚事务
    public void rollback() throws SQLException {
        Connection connection = connectionHolder.get();
        if (connection != null) {
            connection.rollback();
            connection.close();
        }
    }


    // 获取当前事务中的连接
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }



}
