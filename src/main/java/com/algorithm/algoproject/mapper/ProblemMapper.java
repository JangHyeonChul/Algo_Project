package com.algorithm.algoproject.mapper;

import com.algorithm.algoproject.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProblemMapper {

    List<ProblemDTO> selectAllProblems(int offset);
    List<ProblemDTO> selectAllProblemsByType(@Param("typename")String typename, @Param("offset") int offset);
    List<ProblemDTO> selectAllProblemsByOption(@Param("problemOptionDTO") ProblemOptionDTO problemOptionDTO, @Param("offset") int offset);
    List<ProblemDTO> selectAllProblemsBySearch(@Param("keyword")String keyword, @Param("offset") int offset);

    ProblemDTO selectProblem(int pageNum);
    List<SolveHistoryDTO> selectSolveHistorys(@Param("username") String user_id, @Param("p_no") int p_no);
    List<SolveDTO> selectAllSolveMember(String username);

    List<AnswerDTO> selectProblemAnswer(int pageNum);
    SolveDTO selectSolveMember(@Param("username")String username, @Param("p_no")int p_no);
    int selectLastInsertID();
    int selectProblemTypeCount(String typeName);


    void updateAddPoint(@Param("point")int point, @Param("username") String username);

    void insertSolveMember(@Param("p_no") int p_no, @Param("username") String username);
    void insertSolveHistory(SolveHistoryDTO solveHistoryDTO);
    void insertProblem(ProblemDTO problemDTO);
    void insertProblemAnswer(@Param("answer_input")String answerInput,
                             @Param("answer_output")String answerOutput,
                             @Param("p_no")int p_no);

    int countSubmitProblem(String username);
    int countFailProblem(String username);
    int countSuccessProblem(String username);
    int countProblems();
    int countProblemsByOption(ProblemOptionDTO problemOptionDTO);
    Integer countProblemsBySearch(String keyword);

    void deleteProblem(int pageNum);


}
