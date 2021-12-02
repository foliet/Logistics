package cn.rainspace.logistics;

import org.apache.ibatis.io.Resources;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class LogisticsApplicationTests {

    @Test
    void contextLoads() {
        try {
            Resources.getResourceAsFile("EmailMessages/verification.html");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
