package com.edu.graduation.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class BusinessUserMapperTest {

    @Autowired
    private BusinessUserMapper businessUserMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void select(){
//        BusinessUser user = businessUserMapper.getUserById("111");

        String str = passwordEncoder.encode("123456");
        System.out.println(str);
    }

}