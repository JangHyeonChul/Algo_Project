package com.algorithm.algoproject.service;


import com.algorithm.algoproject.dto.home.HomeBoardDTO;
import com.algorithm.algoproject.dto.home.LevelCountDTO;

public interface HomeService {

    LevelCountDTO getHomeLevelCount();
    void homeBoard();

}
