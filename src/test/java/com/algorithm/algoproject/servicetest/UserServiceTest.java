package com.algorithm.algoproject.servicetest;


import com.algorithm.algoproject.dto.MemberDTO;
import com.algorithm.algoproject.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class UserServiceTest {
    
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Test
    void encoder() {
//        MemberDTO userDTO = new MemberDTO("11", "안녕", "11", "11", "11", "11", "11");
//        String userPassword = userDTO.getUser_password();
//
//        String encode = bCryptPasswordEncoder.encode(userPassword);
//        System.out.println("userPassword = " + userPassword);
//        System.out.println("encode = " + encode);
//
//        userDTO.setUser_password(encode);
//
//
//        System.out.println("userDTO = " + userDTO);
//
//        userService.registerUser(userDTO);

    }

    @Test
    void matchTest() {
        String username = "wkdgus1136";
        MemberDTO userdata = userService.findByUserIdOrEmail(username);
        String userPassword = userdata.getUser_password();

        System.out.println("userPassword = " + userPassword);


        if (passwordEncoder.matches("dbslfjqm11", userPassword)) {
            System.out.println("originalPwd = ");
        }
    }
}
