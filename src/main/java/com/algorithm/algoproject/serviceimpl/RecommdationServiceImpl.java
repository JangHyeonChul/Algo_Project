package com.algorithm.algoproject.serviceimpl;

import com.algorithm.algoproject.dto.RecommdationDTO;
import com.algorithm.algoproject.mapper.RecommdationMapper;
import com.algorithm.algoproject.service.RecommdationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecommdationServiceImpl implements RecommdationService {

    @Autowired
    RecommdationMapper recommdationMapper;

    @Override
    public RecommdationDTO writeBoardRecommdation(int b_no) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        RecommdationDTO boardRecommdation = getBoardRecommdation(b_no, username);

        if (boardRecommdation != null) {
            int boardRecommdationCount = recommdationMapper.getBoardRecommdationCount(b_no);
            boardRecommdation.setRecommdationCnt(boardRecommdationCount);
            return boardRecommdation;
        }

        recommdationMapper.insertBoardRecommdation(b_no, username);
        recommdationMapper.updateBoardRecommdationCnt(b_no);
        RecommdationDTO recommdationDTO = recommdationMapper.selectBoardRecommdation(b_no, username);
        int boardRecommdationCount = recommdationMapper.getBoardRecommdationCount(b_no);
        recommdationDTO.setInsertCheck(true);
        recommdationDTO.setRecommdationCnt(boardRecommdationCount);

        return recommdationDTO;

    }

    @Override
    public RecommdationDTO getBoardRecommdation(int b_no, String username) {
        return recommdationMapper.selectBoardRecommdation(b_no, username);
    }


    private RecommdationDTO boardRecommdationDefault(RecommdationDTO boardRecommdation) {
        boardRecommdation.setB_no(0);
        boardRecommdation.setUser_id(null);
        boardRecommdation.setRecom_no(0);
        boardRecommdation.setInsertCheck(true);

        return boardRecommdation;

    }
}
