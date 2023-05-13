package com.algorithm.algoproject.service;


import com.algorithm.algoproject.dto.SolveHistoryDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HistoryService {

    void madeBySolveHistory(String compileResult, String lang, Integer pageNum);
    List<SolveHistoryDTO> getSolveHistorys(String userid, Integer p_no);

}
