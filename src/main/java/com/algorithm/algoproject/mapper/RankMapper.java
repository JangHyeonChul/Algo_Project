package com.algorithm.algoproject.mapper;

import com.algorithm.algoproject.dto.RankDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RankMapper {

    List<RankDTO> selectRank();
    RankDTO selectRankByUserName(String userName);


}
