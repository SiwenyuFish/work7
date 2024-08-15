package spring.jdbc;

import com.spring.context.support.AnnotationConfigApplicationContext;
import com.spring.jdbc.JdbcTemplate;
import com.spring.jdbc.TransactionManager;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;
import spring.bean.User;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class JdbcTest {

    @Test
    public void testJdbc(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/spring");
        dataSource.setUsername("root");
        dataSource.setPassword("297710");
        // 可选的连接池配置参数
        dataSource.setInitialSize(5); // 初始连接数
        dataSource.setMaxTotal(20); // 最大连接数
        dataSource.setMaxIdle(10); // 最大空闲连接数
        dataSource.setMinIdle(5); // 最小空闲连接数
        dataSource.setMaxWaitMillis(10000); // 最大等待时间（毫秒）
        dataSource.setTestOnBorrow(true); // 在从连接池获取连接时测试连接是否有效
        dataSource.setTestWhileIdle(true); // 在连接空闲时测试连接是否有效
        dataSource.setTestOnReturn(false); // 在连接返回连接池时测试连接是否有效
        TransactionManager transactionManager = new TransactionManager(dataSource);

        JdbcTemplate template = new JdbcTemplate(transactionManager);
        String sql = "SELECT id, name FROM user where id = ?";
        try {
            List<User> users = template.query(sql, new UserRowMapper(),1);
            for (User user : users) {
                System.out.println("ID: " + user.getId() + ", Name: " + user.getName());
            }
            User user = template.queryForObject(sql, new UserRowMapper(), 1);
            System.out.println("ID: " + user.getId() + ", Name: " + user.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test2(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JdbcConfig.class);
        JdbcTemplate template = context.getBean(JdbcTemplate.class);
        String sql = "SELECT id, name FROM user where id = ?";
        try {
            List<User> users = template.query(sql, new UserRowMapper(),1);
            for (User user : users) {
                System.out.println("ID: " + user.getId() + ", Name: " + user.getName());
            }
            User user = template.queryForObject(sql, new UserRowMapper(), 1);
            System.out.println("ID: " + user.getId() + ", Name: " + user.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
