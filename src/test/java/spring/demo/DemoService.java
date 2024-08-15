package spring.demo;

import com.spring.core.factory.annotation.Autowired;
import com.spring.core.factory.annotation.Component;
import com.spring.jdbc.JdbcTemplate;
import com.spring.jdbc.Transactional;
import spring.bean.User;
import spring.jdbc.UserRowMapper;

import java.sql.SQLException;
import java.util.List;

@Component
public class DemoService {

    @Autowired
    private static JdbcTemplate template;


    public List<User> getUsers() {
        System.out.println("222");
        String sql = "SELECT id, name FROM user";
        List<User> users = null;

        try {
            users = template.query(sql, new UserRowMapper());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return users;
    }

    @Transactional
    public void addUser(User user) {
        String sql = "INSERT INTO user (name, id) VALUES (?, ?)";
        try {
            template.update(sql,user.getName(),user.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void deleteUser(int id) {
        String sql = "DELETE FROM user WHERE id = ?";
        try {
            template.update(sql,id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void updateUser(String name, int id) {
        String sql = "UPDATE user SET name = ? WHERE id = ?";
        try {
            template.update(sql,name,id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
