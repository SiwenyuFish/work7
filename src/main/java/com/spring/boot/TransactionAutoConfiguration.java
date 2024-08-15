package com.spring.boot;

import com.spring.core.factory.annotation.Bean;
import com.spring.core.factory.annotation.Configuration;
import com.spring.jdbc.JdbcTemplate;
import com.spring.jdbc.TransactionManager;

@Configuration
public class TransactionAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public TransactionManager transactionManager(){
        return new TransactionManager();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate();
    }

}
