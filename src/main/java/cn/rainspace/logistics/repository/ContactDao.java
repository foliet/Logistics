package cn.rainspace.logistics.repository;

import cn.rainspace.logistics.entity.Contact;
import cn.rainspace.logistics.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.util.List;

public class ContactDao implements Dao<Contact>{
    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public int add(Contact contact) {
        String sql = "insert into contacts(create_at,owner_id,province,city,district,telephone,address) value(?,?,?,?,?,?,?)";
        return jdbc.update(sql,new Timestamp(System.currentTimeMillis()), contact.getOwnerId(),contact.getProvince(),contact.getCity(),contact.getDistrict(),contact.getTelephone(),contact.getAddress());
    }

    @Override
    public int delete(int id) {
        String sql = "delete from contacts where id = ?";
        return jdbc.update(sql, id);
    }

    @Override
    public int update(Contact contact) {
        String sql = "update contacts set owner_id = ?,province = ?,city = ?,district = ?,telephone=?,address=? where id = ?";
        return jdbc.update(sql, contact.getOwnerId(),contact.getProvince(),contact.getCity(),contact.getDistrict(),contact.getTelephone(),contact.getAddress(),contact.getId());
    }

    @Override
    public Contact getById(int id) {
        try{
            String sql = "select * from contacts where id = ?";
            return jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(Contact.class), id);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public List<Contact> getAll() {
        String sql = "select * from contacts";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Contact.class));
    }

    public List<Contact> getByOwnerId(int id) {
        String sql = "select * from contacts where owner_id = ?";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Contact.class),id);
    }
}
