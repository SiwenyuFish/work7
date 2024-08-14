package com.spring.jdbc;

import com.spring.context.config.ApplicationContext;
import com.spring.context.config.ApplicationContextAware;
import com.spring.context.support.GenericApplicationContext;
import com.spring.core.beans.BeansException;
import com.spring.core.beans.factory.annotation.Component;
import com.spring.core.beans.factory.config.BeanDefinition;
import com.spring.core.beans.factory.config.InitializingBean;
import com.spring.core.beans.factory.util.BeanFactoryUtil;
import com.spring.web.annotation.Controller;
import com.spring.web.annotation.RequestMapping;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Stack;

public class TransactionManager implements InitializingBean, ApplicationContextAware {

    private DataSource dataSource;
    private ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();
    private ThreadLocal<Connection> nestedConnectionHolder = new ThreadLocal<>();
    private ThreadLocal<TransactionStatus> transactionStatusHolder = new ThreadLocal<>();
    private Stack<TransactionStatus> suspendedTransactions = new Stack<>();
    private GenericApplicationContext applicationContext;

    public TransactionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public TransactionManager() {
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        if (this.applicationContext == null){
            return;
        }
        this.dataSource = applicationContext.getBean(DataSource.class);

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (applicationContext instanceof GenericApplicationContext) {
            this.applicationContext = (GenericApplicationContext) applicationContext;
        }
    }

    // 开始事务
    public void beginTransaction(TransactionDefinition definition) throws SQLException {
        TransactionStatus status = transactionStatusHolder.get();

        if (status != null) {
            switch (definition.getPropagationBehavior()) {
                case TransactionDefinition.PROPAGATION_REQUIRES_NEW:
                    if (status.isNewTransaction()) {
                        throw new IllegalStateException("Existing transaction found, cannot start a new one.");
                    }
                    break;
                case TransactionDefinition.PROPAGATION_NESTED:
                    if (status.isNewTransaction()) {
                        // Create a nested transaction
                        Connection parentConn = connectionHolder.get();
                        if (parentConn != null) {
                            Connection nestedConn = parentConn.getMetaData().getConnection();
                            nestedConnectionHolder.set(nestedConn);
                        }
                    } else {
                        throw new IllegalStateException("No existing transaction found for nested transaction.");
                    }
                    break;
                case TransactionDefinition.PROPAGATION_NOT_SUPPORTED:
                case TransactionDefinition.PROPAGATION_NEVER:
                    throw new UnsupportedOperationException("Unsupported propagation behavior");
            }
        } else {
            // Start a new transaction
            Connection connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            connectionHolder.set(connection);
            transactionStatusHolder.set(new TransactionStatus(true, false));
        }
    }

    // 提交事务
    public void commit() throws SQLException {
        Connection connection = connectionHolder.get();
        if (connection != null) {
            connection.commit();
            connection.close();
            connectionHolder.remove();
            transactionStatusHolder.remove();
        }
    }

    // 回滚事务
    public void rollback() throws SQLException {
        Connection connection = connectionHolder.get();
        if (connection != null) {
            connection.rollback();
            connection.close();
            connectionHolder.remove();
            transactionStatusHolder.remove();
        }
    }

    // 挂起当前事务
    public void suspend() throws SQLException {
        Connection connection = connectionHolder.get();
        if (connection != null) {
            suspendedTransactions.push(transactionStatusHolder.get());
            connectionHolder.remove();
            transactionStatusHolder.remove();
        }
    }

    // 恢复挂起的事务
    public void resume() throws SQLException {
        if (!suspendedTransactions.isEmpty()) {
            TransactionStatus status = suspendedTransactions.pop();
            if (status.isNewTransaction()) {
                Connection connection = dataSource.getConnection();
                connection.setAutoCommit(false);
                connectionHolder.set(connection);
            }
            transactionStatusHolder.set(status);
        } else {
            throw new IllegalStateException("No suspended transactions found.");
        }
    }

    // 获取当前事务中的连接
    public Connection getConnection() {
        return connectionHolder.get();
    }

    // 获取嵌套事务中的连接
    public Connection getNestedConnection() {
        return nestedConnectionHolder.get();
    }

    public static class TransactionStatus {
        private boolean newTransaction;
        private boolean rollbackOnly;

        public TransactionStatus(boolean newTransaction, boolean rollbackOnly) {
            this.newTransaction = newTransaction;
            this.rollbackOnly = rollbackOnly;
        }

        public boolean isNewTransaction() {
            return newTransaction;
        }

        public boolean isRollbackOnly() {
            return rollbackOnly;
        }
    }
}
