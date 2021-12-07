package cn.rainspace.logistics.repository;

import cn.rainspace.logistics.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class UserDao implements Dao<User>{
	@Autowired
	private JdbcTemplate jdbc;

    @Override
	public int add(User user) {
        String sql = "insert into users(username,password,group_id,email,create_at) value(?,?,?,?,?)";
        return jdbc.update(sql, user.getUsername(), user.getPassword(), user.getGroupId(), user.getEmail(),new Timestamp(System.currentTimeMillis()));
    }

    @Override
	public int delete(int id) {
        String sql = "delete from users where id = ?";
        return jdbc.update(sql, id);
    }

    @Override
	public int update(User user) {
        String sql = "update users set username = ?,password = ?,group_id = ?,email = ? where id = ?";
        return jdbc.update(sql, user.getUsername(), user.getPassword(), user.getGroupId(), user.getEmail(), user.getId());
    }
	
	public User getByName(String value) {
        try{
            String sql = "select * from users where username = ?";
            return jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), value);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
	}

    public User getByEmail(String value) {
        try{
            String sql = "select * from users where email = ?";
            return jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), value);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public User getById(int id) {
        try{
            String sql = "select * from users where id = ?";
            return jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public List<User> getAll() {
        String sql = "select * from users";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

}
