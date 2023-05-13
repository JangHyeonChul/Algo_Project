package com.algorithm.algoproject.service;


import com.algorithm.algoproject.dto.MemberDTO;
import com.algorithm.algoproject.dto.MyInfoDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    MemberDTO findByUserIdOrEmail(String userData);
    void registerUser(MemberDTO userDTO);
    void updateUserPassword(String username, String password);
    void updateUserAuthGrade(String username, String grade);

    MyInfoDTO getUserInfo(String username);

    String userModifyInfoValidCheck(String username, String user, String message);

}
