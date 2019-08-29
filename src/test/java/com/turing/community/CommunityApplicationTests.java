package com.turing.community;

import com.turing.community.bean.UserExample;
import com.turing.community.dao.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommunityApplicationTests {
    @Test
    public void contextLoads() {
        System.out.println(UUID.randomUUID().toString().replaceAll("-",""));
    }

}
