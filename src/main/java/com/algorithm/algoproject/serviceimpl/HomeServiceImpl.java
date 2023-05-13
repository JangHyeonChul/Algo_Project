package com.algorithm.algoproject.serviceimpl;

import com.algorithm.algoproject.dto.BoardDTO;
import com.algorithm.algoproject.dto.RankDTO;
import com.algorithm.algoproject.dto.home.HomeBoardDTO;
import com.algorithm.algoproject.dto.home.LevelCountDTO;
import com.algorithm.algoproject.mapper.HomeMapper;
import com.algorithm.algoproject.service.HomeService;
import com.algorithm.algoproject.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class HomeServiceImpl implements HomeService {


    @Autowired
    HomeMapper homeMapper;

    @Autowired
    RankService rankService;




    @Override
    public LevelCountDTO getHomeLevelCount() {
        return homeMapper.selectHomeLevel();
    }

    @Override
    public void homeBoard() {
        Map<String, Objects> homeBoardMap = new HashMap<>();
        List<RankDTO> rankInfo = rankService.getRankInfo();




        for (RankDTO rankDTO : rankInfo) {
            System.out.println("rankDTO = " + rankDTO);
        }
    }
}
