package com.algorithm.algoproject.service;

import com.algorithm.algoproject.PageHandler;
import com.algorithm.algoproject.dto.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProblemService {

    List<ProblemDTO> getAllProblems(int offset);
    List<ProblemDTO> getAllProblemsByType(String typename, int offset);
    List<ProblemDTO> getAllProblemsByOptions(ProblemOptionDTO problemOptionDTO, int offset);
    List<ProblemDTO> getAllProblemsBySearch(String keyword, int offset);
    ProblemDTO getProblem(int pageNum);
    List<AnswerDTO> getProblemAnswers(int pageNum);
    List<SolveDTO> getAllSolveMember(String username);



    void addPoint(int point, String username);

    SolveDTO getSolveMember(String username,int p_no);

    void writeSolveMember(int p_no, String username);
    void writeProblem(ProblemDTO problemDTO);
    void writeProblemAnswer(List<String> input, List<String> output, int p_no);

    int getCountAllProblem();
    int getCountAllProblemsByOption(ProblemOptionDTO problemOptionDTO);
    Integer getCountAllProblemsBySearch(String keyword);

    Map<String, Integer> getProblemTypeCountMap();
    int getProblemTypeCount(String typename);
    int getPageOffset(int page, PageHandler pageHandler);
    int getLastInsertID();
    int getRandomPage(int maxNum);

    void deleteProblem(int pageNum);





}
