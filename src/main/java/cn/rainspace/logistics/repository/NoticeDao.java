package cn.rainspace.logistics.repository;

import cn.rainspace.logistics.entity.Notice;
import cn.rainspace.logistics.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.relational.core.sql.Not;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class NoticeDao implements Dao<Notice>{
    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public int add(Notice notice) {
        String sql = "insert into notices(title,content,owner_id,create_at) value(?,?,?,?)";
        return jdbc.update(sql, notice.getTitle(),notice.getContent(),notice.getOwnerId(),new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public int delete(int id) {
        String sql = "delete from notices where id = ?";
        return jdbc.update(sql, id);
    }

    @Override
    public int update(Notice notice) {
        String sql = "update notices set title = ?,content = ?,owner_id = ? where id = ?";
        return jdbc.update(sql, notice.getTitle(),notice.getContent(),notice.getOwnerId(),notice.getId());
    }

    @Override
    public Notice getById(int id) {
        try{
            String sql = "select * from notices where id = ?";
            return jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(Notice.class), id);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public List<Notice> getAll() {
        String sql = "select * from notices";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Notice.class));
    }

    public List<Notice> getByOwnerId(int id) {
        String sql = "select * from notices where owner_id = ?";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Notice.class),id);
    }
}
