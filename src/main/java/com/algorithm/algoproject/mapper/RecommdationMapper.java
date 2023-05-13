package com.algorithm.algoproject.mapper;

import com.algorithm.algoproject.dto.RecommdationDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RecommdationMapper {

    void insertBoardRecommdation(@Param("b_no") int b_no, @Param("username") String username);
    void updateBoardRecommdationCnt(int b_no);

    RecommdationDTO selectBoardRecommdation(@Param("b_no") int b_no, @Param("username") String username);

    int getBoardRecommdationCount(int b_no);


}
