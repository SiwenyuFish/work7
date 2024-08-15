package com.spring.jdbc;

import com.spring.context.config.ApplicationContext;
import com.spring.context.config.ApplicationContextAware;
import com.spring.context.support.GenericApplicationContext;
import com.spring.core.BeansException;
import com.spring.core.factory.config.InitializingBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate implements InitializingBean, ApplicationContextAware {

    private TransactionManager transactionManager;
    private GenericApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (applicationContext instanceof GenericApplicationContext) {
            this.applicationContext = (GenericApplicationContext) applicationContext;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(applicationContext == null)
            return;
        this.transactionManager = applicationContext.getBean(TransactionManager.class);
    }

    public JdbcTemplate() {
    }

    public JdbcTemplate(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    // 执行更新操作
    public int update(String sql, Object... params) throws SQLException {
        try (Connection conn = transactionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // 设置参数
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            // 执行更新
            return ps.executeUpdate();
        }
    }

    // 执行查询操作
    public <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... params) throws SQLException {
        try (Connection conn = transactionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // 设置参数
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rowMapper.mapRow(rs, 1);
                }
                return null;
            }
        }
    }

    // 执行查询列表操作
    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... params) throws SQLException {
        try (Connection conn = transactionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // 设置参数
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            try (ResultSet rs = ps.executeQuery()) {
                List<T> results = new ArrayList<>();
                int rowNum = 1;
                while (rs.next()) {
                    results.add(rowMapper.mapRow(rs, rowNum++));
                }
                return results;
            }
        }
    }


}
