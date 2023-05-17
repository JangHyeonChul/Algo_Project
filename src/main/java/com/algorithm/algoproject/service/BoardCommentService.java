package com.algorithm.algoproject.service;

import com.algorithm.algoproject.dto.CommentDTO;

import java.util.List;

public interface BoardCommentService {

    void writeBoardComment(int boardNumber, String commentContent);
    void updateBoardCommentCnt(int b_no);
    void deleteBoardComment(int pageNum);
    void updateBoardComment(int c_no, String content);

    List<CommentDTO> getBoardComments(int b_no);
    CommentDTO getBoardComment(int c_no);

    int getBoardCommentCount(int b_no);
}
