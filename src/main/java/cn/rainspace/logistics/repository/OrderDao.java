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
        String sql = "insert into orders(create_at,sender_contact_id,receiver_contact_id,title,remark ,value,volume,weight,status) value(?,?,?,?,?,?,?,?,?)";
        return jdbc.update(sql, new Timestamp(System.currentTimeMillis()), order.getSenderContactId(), order.getReceiverContactId(), order.getTitle(), order.getRemark(), order.getValue(), order.getVolume(), order.getWeight(), order.getStatus());
    }

    @Override
    public int delete(int id) {
        String sql = "delete from orders where id = ?";
        return jdbc.update(sql, id);
    }

    @Override
    public int update(Order order) {
        String sql = "update orders set sender_contact_id=?,receiver_contact_id=?,title=?,remark=?,value=?,volume=?,weight=?,status=?,chunk_id=?,staff_id=? where id = ?";
        return jdbc.update(sql, order.getSenderContactId(), order.getReceiverContactId(), order.getTitle(), order.getRemark(), order.getValue(), order.getVolume(), order.getWeight(), order.getStatus()
                , order.getChunkId(), order.getStaffId(), order.getId());
    }

    @Override
    public Order getById(int id) {
        try {
            String sql = "select * from orders where id = ?";
            return jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(Order.class), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Order> getAll() {
        String sql = "select orders.*,ua.username sender_name,ub.username receiver_name from orders,contacts ca,contacts cb,users ua,users ub where orders.sender_contact_id = ca.id and orders.receiver_contact_id = cb.id and ca.receiver_id = ua.id and cb.receiver_id = ub.id";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Order.class));
    }

    public List<Order> getBySenderId(int id) {
        String sql = "select orders.*,ua.username sender_name,ub.username receiver_name from orders,contacts ca,contacts cb,users ua,users ub where orders.sender_contact_id = ca.id and orders.receiver_contact_id = cb.id and ca.receiver_id = ua.id and cb.receiver_id = ub.id and ua.id = ?";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Order.class), id);
    }

    public List<Order> getByReceiverId(int id) {
        String sql = "select orders.*,ua.username sender_name,ub.username receiver_name from orders,contacts ca,contacts cb,users ua,users ub where orders.sender_contact_id = ca.id and orders.receiver_contact_id = cb.id and ca.receiver_id = ua.id and cb.receiver_id = ub.id and ub.id = ?";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Order.class), id);
    }
}
