package cn.rainspace.logistics;

import cn.rainspace.logistics.entity.User;
import cn.rainspace.logistics.repository.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class LogisticsApplicationTests {

    @Autowired
    private UserDao userDao;

    @Test
    void contextLoads() {
        List<User> users = userDao.getAll();
        Timestamp timestamp = users.get(0).getCreateAt();
        LocalDate localDate = timestamp.toLocalDateTime().toLocalDate();
    }

}
