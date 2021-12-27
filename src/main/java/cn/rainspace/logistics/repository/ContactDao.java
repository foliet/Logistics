package cn.rainspace.logistics.repository;

import cn.rainspace.logistics.entity.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class ContactDao implements Dao<Contact> {
    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public int add(Contact contact) {
        String sql = "insert into contacts(create_at,owner_id,receiver_id,province,city,district,telephone,address) value(?,?,?,?,?,?,?,?)";
        return jdbc.update(sql, new Timestamp(System.currentTimeMillis()), contact.getOwnerId(), contact.getReceiverId(), contact.getProvince(), contact.getCity(), contact.getDistrict(), contact.getTelephone(), contact.getAddress());
    }

    @Override
    public int delete(int id) {
        String sql = "delete from contacts where id = ?";
        return jdbc.update(sql, id);
    }

    @Override
    public int update(Contact contact) {
        String sql = "update contacts set owner_id = ?,receiver_id=?,province = ?,city = ?,district = ?,telephone=?,address=? where id = ?";
        return jdbc.update(sql, contact.getOwnerId(), contact.getReceiverId(), contact.getProvince(), contact.getCity(), contact.getDistrict(), contact.getTelephone(), contact.getAddress(), contact.getId());
    }

    @Override
    public Contact getById(int id) {
        try {
            String sql = "select * from contacts where id = ?";
            return jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(Contact.class), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Contact> getAll() {
        String sql = "select * from contacts";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Contact.class));
    }

    public List<Contact> getByOwnerId(int id) {
        String sql = "select contacts.*,ua.username ownerName,ub.username receiverName from contacts,users ua,users ub where contacts.owner_id=ua.id and contacts.receiver_id=ub.id and owner_id = ?";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Contact.class), id);
    }
}
