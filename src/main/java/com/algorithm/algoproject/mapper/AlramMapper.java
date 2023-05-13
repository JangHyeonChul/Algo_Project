package com.algorithm.algoproject.mapper;

import com.algorithm.algoproject.dto.AlramDTO;
import com.algorithm.algoproject.dto.join.AlramBoardDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AlramMapper {

    void insertBoardAlram(AlramDTO alramDTO);
    void updateBoardAlram(@Param("username") String username, @Param("b_no") int b_no);

    List<AlramDTO> selectBoardAlram(@Param("username") String username, @Param("b_no") int b_no);
    List<AlramBoardDTO> selectBoardAlrams(String username);
}
