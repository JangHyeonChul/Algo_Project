package com.algorithm.algoproject.service;

import com.algorithm.algoproject.dto.CommentDTO;

import java.util.List;

public interface BoardCommentService {

    void writeBoardComment(int boardNumber, String commentContent);
    void updateBoardCommentCnt(int b_no);

    List<CommentDTO> getBoardComments(int b_no);

    int getBoardCommentCount(int b_no);
}
