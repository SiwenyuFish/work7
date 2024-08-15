package spring.jdbc;


import com.spring.core.factory.annotation.Bean;
import com.spring.core.factory.annotation.Configuration;
import com.spring.jdbc.JdbcTemplate;
import com.spring.jdbc.TransactionManager;

@Configuration
public class JdbcConfig {

    @Bean
    public TransactionManager transactionManager(){
        return new TransactionManager();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate();
    }
}
