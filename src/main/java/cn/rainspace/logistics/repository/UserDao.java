package cn.rainspace.logistics.repository;

import cn.rainspace.logistics.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class UserDao {
	@Autowired
	private JdbcTemplate jdbc;
	public int addUser(User user) {
        String sql = "insert into users(username,password,group_id,email,create_at) value(?,?,?,?,?)";
        return jdbc.update(sql, new Object[]{user.getUsername(), user.getPassword(), user.getGroup_id(), user.getEmail(),new Timestamp(System.currentTimeMillis())});
    }
	public int deleteUser(int id) {
        String sql = "delete from users where id = ?";
        return jdbc.update(sql, new Object[]{new Timestamp(System.currentTimeMillis()),id});
    }
	public int updateUser(User user) {
        String sql = "update users set username = ?,password = ?,group_id = ?,email = ? where id = ?";
        return jdbc.update(sql, new Object[]{user.getUsername(), user.getPassword(), user.getGroup_id(), user.getEmail(), user.getId()});
    }
	
	public List<User> listUserByName(String value) {
        String sql = "select * from users where username = ?";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(User.class), value);
	}

    public List<User> listUserByEmail(String value) {
        String sql = "select * from users where email = ?";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(User.class), value);
    }

    public List<User> listUserById(int value) {
        String sql = "select * from users where id = ?";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(User.class), value);
    }

}
