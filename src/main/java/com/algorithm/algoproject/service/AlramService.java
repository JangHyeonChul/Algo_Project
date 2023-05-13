package com.algorithm.algoproject.service;

import com.algorithm.algoproject.dto.AlramDTO;
import com.algorithm.algoproject.dto.join.AlramBoardDTO;

import java.util.List;

public interface AlramService {

    void writeBoardAlram(AlramDTO alramDTO);
    void updateBoardAlram(int b_no);
    List<AlramBoardDTO> getBoardAlrams(String username);
}
