package cn.rainspace.logistics.repository;

import cn.rainspace.logistics.entity.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class StaffDao implements Dao<Staff> {
    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public int add(Staff staff) {
        String sql = "insert into staffs(name,gender,create_at) value(?,?,?)";
        return jdbc.update(sql, staff.getName(), staff.getGender(), new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public int delete(int id) {
        String sql = "delete from staffs where id = ?";
        return jdbc.update(sql, id);
    }

    @Override
    public int update(Staff staff) {
        String sql = "update staffs set status = ?,name = ?,gender = ? where id = ?";
        return jdbc.update(sql, staff.getStatus(), staff.getName(), staff.getGender(), staff.getId());
    }

    @Override
    public Staff getById(int id) {
        try {
            String sql = "select * from staffs where id = ?";
            return jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(Staff.class), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Staff> getAll() {
        String sql = "select * from staffs";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Staff.class));
    }
}
