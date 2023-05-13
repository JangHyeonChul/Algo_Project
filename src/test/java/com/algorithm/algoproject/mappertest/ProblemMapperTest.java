package com.algorithm.algoproject.mappertest;


import com.algorithm.algoproject.dto.*;
import com.algorithm.algoproject.mapper.ProblemMapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
public class ProblemMapperTest {

    @Autowired
    ProblemMapper problemMapper;


    @Test
    @Transactional
    @DisplayName("모든문제데이터얻기")
    void selectAllProblems() {



        List<ProblemDTO> problemDTOs = problemMapper.selectAllProblems(3);

        for(ProblemDTO problemDTO1 : problemDTOs ) {
            System.out.println("problemDTO1 = " + problemDTO1);
        }


        Assertions.assertThat(problemDTOs).isNotEmpty();

    }

    @Test
    @DisplayName("문제전체갯수")
    void countProblems() {
        int problemCount = problemMapper.countProblems();

        Assertions.assertThat(problemCount).isNotZero();
    }
    
    @Test
    @DisplayName("게시물번호로게시물가져오기")
    void selectProblem() {
        ProblemDTO problemDTO = problemMapper.selectProblem(1);

        Assertions.assertThat(problemDTO).isNotNull();
        
    }

    @Test
    @DisplayName("회원가입데이터넣기")
    void insertUser() {

    }

    @Test
    @DisplayName("정답지가져오기")
    void selectProblemAnswer() {
        List<AnswerDTO> answerDTOS = problemMapper.selectProblemAnswer(1);

        for(AnswerDTO answerDTO : answerDTOS) {
            System.out.println("answerDTO = " + answerDTO);
        }

    }

    @Test
    @DisplayName("제출내역")
    void insertSolveHistory() {
//        SolveHistoryDTO solveHistoryDTO = SolveHistoryDTO.builder()
//                .user_id("wkdgus1136")
//                .p_no(1)
//                .success_chk("맞췄습니다")
//                .submit_lang("JAVA")
//                .submit_fail("컴파일에러")
//                .build();
//
//
//        problemMapper.insertSolveHistory(solveHistoryDT
    }

    @Test
    @DisplayName("제출내역가져오기")
    void selectSolveHistorys() {

//        List<SolveHistoryDTO> solveHistoryDTOS = problemMapper.selectSolveHistorys("wkdgus1136", 1);
//
//        for(SolveHistoryDTO solveHistoryDTO : solveHistoryDTOS) {
//            System.out.println("solveHistoryDTO = " + solveHistoryDTO);
//        }

    }

    @Test
    @DisplayName("포인트더하기")
    void updateAddPoint() {
        problemMapper.updateAddPoint(10, "wkdgus1136");
    }

    @Test
    @DisplayName("푼문제")
    void insertSolve() {
        problemMapper.insertSolveMember(1, "wkdgus1136");

    }

    @Test
    @DisplayName("푼문제")
    void selectSolve() {
//        SolveDTO solve = problemMapper.selectSolveMember("wkdgus11361");
//        System.out.println("solve = " + solve);


    }

    @Test
    void selectAllProblemsByType() {
        List<ProblemDTO> string = problemMapper.selectAllProblemsByType("사칙연산", 0);

        for (ProblemDTO problem : string) {
            System.out.println("problem = " + problem);
        }
    }

    @Test
    void selectAllProblmesByOption() {
        ProblemOptionDTO problemOptionDTO = new ProblemOptionDTO();
        problemOptionDTO.setOrder("asc");
        problemOptionDTO.setCategory("문자열");


        List<ProblemDTO> problems = problemMapper.selectAllProblemsByOption(problemOptionDTO, 0);
        int i = problemMapper.countProblemsByOption(problemOptionDTO);

        System.out.println("i = " + i);

        for (ProblemDTO problem : problems) {
            System.out.println("problem = " + problem);
        }
    }






}
