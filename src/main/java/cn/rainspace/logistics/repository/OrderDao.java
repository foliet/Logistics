package cn.rainspace.logistics.repository;

import cn.rainspace.logistics.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class OrderDao implements Dao<Order> {
    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public int add(Order order) {
        String sql = "insert into orders(create_at,sender_id,sender_contact_id,receiver_id,receiver_contact_id,title,describe ,value,volume,weight,status,chunk_id,driver_id) value(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        return jdbc.update(sql,new Timestamp(System.currentTimeMillis()),order.getSenderId(),order.getSenderContactId(),order.getReceiverId(),order.getReceiverContactId(),order.getTitle(),order.getDescribe(),order.getValue(),order.getVolume(),order.getWeight(),order.getStatus(),order.getChunkId(),order.getDriverId());
    }

    @Override
    public int delete(int id) {
        String sql = "delete from orders where id = ?";
        return jdbc.update(sql, id);
    }

    @Override
    public int update(Order order) {
        String sql = "update orders set sender_id=?,sender_contact_id=?,receiver_id=?,receiver_contact_id=?,title=?,describe=?,value=?,volume=?,weight=?,status=?,chunk_id=?,driver_id=? where id = ?";
        return jdbc.update(sql,order.getSenderId(),order.getSenderContactId(),order.getReceiverId(),order.getReceiverContactId(),order.getTitle(),order.getDescribe(),order.getValue(),order.getVolume(),order.getWeight(),order.getStatus(),order.getChunkId(),order.getDriverId(),order.getId());
    }

    @Override
    public Order getById(int id) {
        try{
            String sql = "select * from orders where id = ?";
            return jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(Order.class), id);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public List<Order> getAll() {
        String sql = "select * from orders";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Order.class));
    }

    public List<Order> getBySenderId(int id){
        String sql = "select * from orders where sender_id = ?";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Order.class),id);
    }

    public List<Order> getByReceiverId(int id){
        String sql = "select * from orders where receiver_id = ?";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Order.class),id);
    }
}
