package com.algorithm.algoproject.serviceimpl;

import com.algorithm.algoproject.dto.RankDTO;
import com.algorithm.algoproject.mapper.RankMapper;
import com.algorithm.algoproject.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RankServiceImpl implements RankService {

    @Autowired
    RankMapper rankMapper;

    @Override
    public List<RankDTO> getRankInfo() {

        return rankMapper.selectRank();
    }

    @Override
    public List<RankDTO> getRankInfoHome() {
        List<RankDTO> rankList = new ArrayList<>();
        List<RankDTO> rankDTOs = rankMapper.selectRank();

        for (int i=0; i<4; i++) {
            rankList.add(rankDTOs.get(i));
        }

        return rankList;
    }

    @Override
    public RankDTO getRankInfoByUserName(String userName) {

        return rankMapper.selectRankByUserName(userName);
    }
}
