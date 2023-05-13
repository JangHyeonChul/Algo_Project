package com.algorithm.algoproject.service;

import com.algorithm.algoproject.dto.RankDTO;

import java.util.List;

public interface RankService {

    List<RankDTO> getRankInfo();
    List<RankDTO> getRankInfoHome();
    RankDTO getRankInfoByUserName(String userName);


}
