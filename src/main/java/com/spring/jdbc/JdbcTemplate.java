package com.spring.jdbc;

import com.spring.context.config.ApplicationContext;
import com.spring.context.config.ApplicationContextAware;
import com.spring.context.support.GenericApplicationContext;
import com.spring.core.beans.BeansException;
import com.spring.core.beans.factory.config.InitializingBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // 执行查询单个对象操作
    public <T> T queryForObject(String sql, Class<T> requiredType) throws SQLException {
        try (Connection conn = transactionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return convertValue(rs, requiredType);
                }
                return null;
            }
        }
    }

    // 执行查询列表操作（Map形式）
    public List<Map<String, Object>> queryForList(String sql) throws SQLException {
        try (Connection conn = transactionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                List<Map<String, Object>> results = new ArrayList<>();
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnLabel(i);
                        Object value = rs.getObject(i);
                        row.put(columnName, value);
                    }
                    results.add(row);
                }
                return results;
            }
        }
    }

    // 辅助方法：将 ResultSet 转换为指定类型的单个值
    private <T> T convertValue(ResultSet rs, Class<T> requiredType) throws SQLException {
        if (requiredType == String.class) {
            return requiredType.cast(rs.getString(1));
        } else if (requiredType == Integer.class) {
            return requiredType.cast(rs.getInt(1));
        } else if (requiredType == Double.class) {
            return requiredType.cast(rs.getDouble(1));
        } else if (requiredType == Boolean.class) {
            return requiredType.cast(rs.getBoolean(1));
        } else if (requiredType == Long.class) {
            return requiredType.cast(rs.getLong(1));
        } else if (requiredType == Float.class) {
            return requiredType.cast(rs.getFloat(1));
        } else {
            throw new IllegalArgumentException("Unsupported type: " + requiredType.getName());
        }
    }
}
