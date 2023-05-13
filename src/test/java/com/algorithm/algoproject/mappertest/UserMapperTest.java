package com.algorithm.algoproject.mappertest;


import com.algorithm.algoproject.dto.MemberDTO;
import com.algorithm.algoproject.mapper.UserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserMapperTest {
    
    @Autowired
    UserMapper userMapper;
    
    @Test
    @DisplayName("아이디및이메일중복체크") 
    void 아이디이메일중복체크() {
        MemberDTO result = userMapper.findByUserData("wkdgus1136");

    }

    @Test
    @DisplayName("아이디찾기")
    void findByUserId() {
        MemberDTO memberDTO = userMapper.findByUserId("wkdgus1136");

        System.out.println("memberDTO = " + memberDTO);
    }

}
