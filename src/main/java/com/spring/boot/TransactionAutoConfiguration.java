package com.spring.boot;

import com.spring.core.beans.factory.annotation.Bean;
import com.spring.core.beans.factory.annotation.Configuration;
import com.spring.jdbc.JdbcTemplate;
import com.spring.jdbc.TransactionManager;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

@Configuration
public class TransactionAutoConfiguration {

    @Bean
    public TransactionManager transactionManager(){
        return new TransactionManager();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate();
    }

    @Bean
    @ConditionalOnMissingBean
    public DataSource dataSource(){
        return new BasicDataSource();
    }


}
