package com.algorithm.algoproject.service;

import com.algorithm.algoproject.dto.RecommdationDTO;

public interface RecommdationService {

    RecommdationDTO writeBoardRecommdation(int b_no);

    RecommdationDTO getBoardRecommdation(int b_no, String username);
}
