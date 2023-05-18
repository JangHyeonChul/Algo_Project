package com.algorithm.algoproject.serviceimpl;


import com.algorithm.algoproject.config.AuthorityConstains;
import com.algorithm.algoproject.dto.MemberDTO;
import com.algorithm.algoproject.dto.MyInfoDTO;
import com.algorithm.algoproject.mapper.ProblemMapper;
import com.algorithm.algoproject.mapper.UserMapper;
import com.algorithm.algoproject.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class UserServiceImpl implements UserService {

    BCryptPasswordEncoder bCryptPasswordEncoder;
    UserMapper userMapper;
    ProblemMapper problemMapper;

    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserMapper userMapper, ProblemMapper problemMapper) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userMapper = userMapper;
        this.problemMapper = problemMapper;
    }

    @Override
    public MemberDTO findByUserIdOrEmail(String userData) {
        return userMapper.findByUserData(userData);
    }

    @Override
    public void registerUser(MemberDTO userDTO) {
        String userPassword = userDTO.getUser_password();
        String encodeUserPassword = bCryptPasswordEncoder.encode(userPassword);
        userDTO.setUser_password(encodeUserPassword);
        userDTO.setUser_role(AuthorityConstains.ROLE_UN_AUTHUSER);

        userMapper.insertUser(userDTO);
    }

    @Override
    public void updateUserPassword(String username, String password) {
        userMapper.updateUserPassword(username, password);
    }

    @Override
    public void updateUserAuthGrade(String username, String grade) {
        userMapper.updateUserAuthGrade(username, grade);
    }

    @Override
    public MyInfoDTO getUserInfo(String username) {
        int countSuccessProblem = problemMapper.countSuccessProblem(username);
        int countFailProblem = problemMapper.countFailProblem(username);
        int countSubmitProblem = problemMapper.countSubmitProblem(username);
        MemberDTO member = userMapper.findByUserId(username);

        LocalDateTime createAt = member.getCreate_at();
        String createFormat = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일").format(createAt);

        MyInfoDTO myInfoDTO = MyInfoDTO.builder()
                .successProblem(countSuccessProblem)
                .failProblem(countFailProblem)
                .submitProblem(countSubmitProblem)
                .point(member.getUser_point())
                .user_role(member.getUser_role())
                .grade(member.getUser_grade())
                .email(member.getUser_email())
                .create_at(createFormat)
                .build();


        return myInfoDTO;
    }


    @Override
    public String userModifyInfoValidCheck(String username, String user, String message) {

        if (!username.equals(user)) {
            return "error";
        }

        writeUserModifyInfo(username, message);
        return "success";
    }

    private void writeUserModifyInfo(String username, String message) {
        userMapper.updateUserMessage(username, message);
    }
}
