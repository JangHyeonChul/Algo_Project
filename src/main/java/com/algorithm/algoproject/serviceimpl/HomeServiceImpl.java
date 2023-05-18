package com.algorithm.algoproject.serviceimpl;


import com.algorithm.algoproject.dto.RankDTO;
import com.algorithm.algoproject.dto.home.LevelCountDTO;
import com.algorithm.algoproject.mapper.HomeMapper;
import com.algorithm.algoproject.service.HomeService;
import com.algorithm.algoproject.service.RankService;
import org.springframework.stereotype.Service;


@Service
public class HomeServiceImpl implements HomeService {

    HomeMapper homeMapper;
    RankService rankService;

    public HomeServiceImpl(HomeMapper homeMapper, RankService rankService) {
        this.homeMapper = homeMapper;
        this.rankService = rankService;
    }

    @Override
    public LevelCountDTO getHomeLevelCount() {
        return homeMapper.selectHomeLevel();
    }

}
