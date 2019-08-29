package com.turing.community;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableCaching
@MapperScan("com.turing.community.dao")
public class CommunityApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext=SpringApplication.run(CommunityApplication.class, args);
    }

}
