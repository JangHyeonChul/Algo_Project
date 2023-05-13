package com.algorithm.algoproject.mapper;

import com.algorithm.algoproject.dto.CommentDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardCommentMapper {

    void insertBoardComment(CommentDTO commentDTO);
    void updateBoardComment(int boardNum);

    List<CommentDTO> selectBoardComments(int b_no);

    int selectBoardCommentCount(int b_no);

}
