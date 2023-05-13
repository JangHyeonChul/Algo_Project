package com.algorithm.algoproject.serviceimpl;

import com.algorithm.algoproject.PageHandler;
import com.algorithm.algoproject.dto.*;
import com.algorithm.algoproject.mapper.ProblemMapper;
import com.algorithm.algoproject.service.ProblemService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class ProblemServiceImpl implements ProblemService {

    @Autowired
    ProblemMapper problemMapper;

    private String ProblemsHandler(String typename) {
        if (typename.equals("ac"))
            return "사칙연산";

        if (typename.equals("st"))
            return "문자열";

        if (typename.equals("so"))
            return "정렬";

        return null;
    }


    @Override
    public List<ProblemDTO> getAllProblems(int offset) {
        return problemMapper.selectAllProblems(offset);
    }

    @Override
    public List<ProblemDTO> getAllProblemsByType(String typename, int offset) {
        String TransTypeName = ProblemsHandler(typename);

        return problemMapper.selectAllProblemsByType(TransTypeName, offset);
    }

    @Override
    public List<ProblemDTO> getAllProblemsByOptions(ProblemOptionDTO problemOptionDTO, int offset) {
        return problemMapper.selectAllProblemsByOption(problemOptionDTO, offset);
    }

    @Override
    public List<ProblemDTO> getAllProblemsBySearch(String keyword, int offset) {
        return problemMapper.selectAllProblemsBySearch(keyword, offset);
    }

    @Override
    public ProblemDTO getProblem(int pageNum) {
        return problemMapper.selectProblem(pageNum);
    }

    @Override
    public List<AnswerDTO> getProblemAnswers(int pageNum) {
        return problemMapper.selectProblemAnswer(pageNum);
    }

    @Override
    public List<SolveDTO> getAllSolveMember(String username) {
        return problemMapper.selectAllSolveMember(username);
    }

    @Override
    public void addPoint(int point, String username) {
        problemMapper.updateAddPoint(point, username);
    }

    @Override
    public SolveDTO getSolveMember(String username, int p_no) {

        return problemMapper.selectSolveMember(username, p_no);
    }

    @Override
    public void writeSolveMember(int p_no, String username) {
        problemMapper.insertSolveMember(p_no, username);
    }


    @Override
    public int getCountAllProblem() {
        return problemMapper.countProblems();
    }

    @Override
    public int getCountAllProblemsByOption(ProblemOptionDTO problemOptionDTO) {
        return problemMapper.countProblemsByOption(problemOptionDTO);
    }

    @Override
    public Integer getCountAllProblemsBySearch(String keyword) {
        return problemMapper.countProblemsBySearch(keyword);
    }


    @Override
    public Map<String, Integer> getProblemTypeCountMap() {
        Map<String, Integer> problemTypeCountMap = new HashMap<>();

        int arithmetic = problemMapper.selectProblemTypeCount("사칙연산");
        int string = problemMapper.selectProblemTypeCount("문자열");
        int sorting = problemMapper.selectProblemTypeCount("정렬");

        problemTypeCountMap.put("arithmetic", arithmetic);
        problemTypeCountMap.put("string", string);
        problemTypeCountMap.put("sorting", sorting);


        return problemTypeCountMap;
    }

    @Override
    public int getProblemTypeCount(String typename) {
        String TransTypeName = ProblemsHandler(typename);

        return problemMapper.selectProblemTypeCount(TransTypeName);
    }

    @Override
    public int getPageOffset(int page, PageHandler pageHandler) {

        return (page - 1)* pageHandler.getPageSize();
    }

    @Override
    public void writeProblem(ProblemDTO problemDTO) {

        problemMapper.insertProblem(problemDTO);
    }

    @Override
    public void writeProblemAnswer(List<String> inputs, List<String> outputs, int p_no) {
        Iterator<String> input = inputs.iterator();
        Iterator<String> output = outputs.iterator();

        while(input.hasNext() && output.hasNext()) {
            String getInput = input.next();
            String getOutput = output.next();

            problemMapper.insertProblemAnswer(getInput, getOutput, p_no);
        }

    }

    @Override
    public int getLastInsertID() {
        return problemMapper.selectLastInsertID();
    }

    @Override
    public int getRandomPage(int maxNum) {
        int min = 1;

        return (int) (Math.random() * (maxNum - min + 1)) + min;
    }


}
