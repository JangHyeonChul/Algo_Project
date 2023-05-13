package com.algorithm.algoproject.mapper;


import com.algorithm.algoproject.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    MemberDTO findByUserData(String userData);
    void insertUser(MemberDTO memberDTO);
    void updateUserMessage(@Param("username")String username, @Param("message") String message);
    void updateUserPassword(@Param("username") String username, @Param("password") String password);
    void updateUserAuthGrade(@Param("username") String username, @Param("grade") String grade);



    MemberDTO findByUserId(String userId);
}
