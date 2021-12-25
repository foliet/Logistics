package cn.rainspace.logistics.repository;

import cn.rainspace.logistics.entity.Chunk;
import cn.rainspace.logistics.entity.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class ChunkDao implements Dao<Chunk>{
    @Autowired
    JdbcTemplate jdbc;
    @Override
    public int add(Chunk chunk) {
        String sql = "insert into chunks(number,model,create_at) value(?,?,?)";
        return jdbc.update(sql,chunk.getNumber(),chunk.getModel(),new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public int delete(int id) {
        String sql = "delete from chunks where id = ?";
        return jdbc.update(sql, id);
    }

    @Override
    public int update(Chunk chunk) {
        String sql = "update chunks set status = ?,number = ?,model = ? where id = ?";
        return jdbc.update(sql,chunk.getStatus(),chunk.getNumber(),chunk.getModel(),chunk.getId());
    }

    @Override
    public Chunk getById(int id) {
        try{
            String sql = "select * from chunks where id = ?";
            return jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(Chunk.class), id);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public List<Chunk> getAll() {
        String sql = "select * from chunks";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Chunk.class));
    }
}
