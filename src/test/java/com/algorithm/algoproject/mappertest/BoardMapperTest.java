package com.algorithm.algoproject.mappertest;


import com.algorithm.algoproject.Time;
import com.algorithm.algoproject.dto.BoardDTO;
import com.algorithm.algoproject.dto.CommentDTO;
import com.algorithm.algoproject.mapper.BoardMapper;
import com.algorithm.algoproject.service.BoardCommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class BoardMapperTest {

    @Autowired
    BoardMapper boardMapper;

    @Autowired
    BoardCommentService boardCommentService;

    @Test
    void insertBoard() {
        BoardDTO boardDTO = new BoardDTO("wkdgus1136", "제목", "카테고리", "내용", "언어", "true");

        boardMapper.insertBoard(boardDTO);
    }

    @Test
    void selectAllBoards() {
        List<BoardDTO> qu = boardMapper.selectAllBoards(0, "QU");

        for(BoardDTO boardDTO : qu) {
            Date boardDate = boardDTO.getB_create();
            Time transtime = new Time();
//            String time = transtime.txtDate(boardDate);
//            System.out.println("time = " + time);
            System.out.println("boardDTO = " + boardDTO);
        }
    }

    @Test
    void timeTest() {
        List<CommentDTO> boardComments = boardCommentService.getBoardComments(29);
        Time time1 = new Time();

        for(CommentDTO commentDTO : boardComments) {
            Date cCreate = commentDTO.getC_create();
//            String result = time1.txtDate(cCreate);
//            System.out.println("result = " + result);
        }
    }
}
