package com.algorithm.algoproject.serviceimpl;

import com.algorithm.algoproject.Time;
import com.algorithm.algoproject.config.CompileConstains;
import com.algorithm.algoproject.dto.SolveHistoryDTO;
import com.algorithm.algoproject.mapper.ProblemMapper;
import com.algorithm.algoproject.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    ProblemMapper problemMapper;

    @Override
    public void madeBySolveHistory(String compileResult, String lang, Integer pageNum) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            String compileMessage = HistoryMessage(compileResult);
            String compileCheck = HistorySuccessMessage(compileResult);

            SolveHistoryDTO solveHistoryDTO = new SolveHistoryDTO(name, pageNum, compileCheck, lang, compileMessage);



            problemMapper.insertSolveHistory(solveHistoryDTO);
        }

    }

    @Override
    public List<SolveHistoryDTO> getSolveHistorys(String userid, Integer p_no) {
        List<SolveHistoryDTO> solveHistoryDTOS = problemMapper.selectSolveHistorys(userid, p_no);

        List<SolveHistoryDTO> solveHistoryDTO = solveHistoryTimeConverter(solveHistoryDTOS);

        return solveHistoryDTO;
    }


    private String HistoryMessage(String compileResult) {

        boolean compileFail = compileResult.equals(CompileConstains.COMPILE_FAIL);
        boolean compileError = compileResult.equals(CompileConstains.COMPILE_ERROR);

        if (compileFail) {
            return CompileConstains.COMPILE_FAIL_MESSAGE;
        }

        if (compileError) {
            return CompileConstains.COMPILE_ERROR_MESSAGE;
        }

        return "";

    }

    private String HistorySuccessMessage(String compileResult) {

        boolean compileFail = compileResult.equals(CompileConstains.COMPILE_FAIL);
        boolean compileError = compileResult.equals(CompileConstains.COMPILE_ERROR);

        if (compileFail|| compileError) {
            return CompileConstains.COMPILE_FAIL_CHECK_MESSAGE;
        } else {
            return CompileConstains.COMPILE_SUCCESS_MESSAGE;
        }

    }

    private List<SolveHistoryDTO> solveHistoryTimeConverter(List<SolveHistoryDTO> solveHistoryDTOs) {

        for (SolveHistoryDTO solveHistoryDTO : solveHistoryDTOs) {
            LocalDateTime submitDate = solveHistoryDTO.getSubmit_date();
            String converterTime = Time.txtDate(submitDate);
            solveHistoryDTO.setTransTime(converterTime);
        }

        return solveHistoryDTOs;

    }

}
