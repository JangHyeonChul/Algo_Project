package com.algorithm.algoproject.serviceimpl;

import com.algorithm.algoproject.dto.AlramDTO;
import com.algorithm.algoproject.dto.BoardDTO;
import com.algorithm.algoproject.dto.join.AlramBoardDTO;
import com.algorithm.algoproject.mapper.AlramMapper;
import com.algorithm.algoproject.mapper.BoardMapper;
import com.algorithm.algoproject.service.AlramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class AlramServiceImpl implements AlramService {

    @Autowired
    AlramMapper alramMapper;

    @Autowired
    BoardMapper boardMapper;

    @Override
    public void writeBoardAlram(AlramDTO alramDTO) {
        alramMapper.insertBoardAlram(alramDTO);
    }

    @Override
    public void updateBoardAlram(int b_no) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<AlramDTO> alramDTOs = alramMapper.selectBoardAlram(username, b_no);

        if (!Objects.isNull(alramDTOs)) {
            for (AlramDTO alramDTO : alramDTOs) {
                if (alramDTO.getA_check().equals("0")) {
                    alramMapper.updateBoardAlram(username, b_no);
                }
            }
        }
    }

    @Override
    public List<AlramBoardDTO> getBoardAlrams(String username) {
        return alramMapper.selectBoardAlrams(username);
    }
}
