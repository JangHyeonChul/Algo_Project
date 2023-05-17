package com.algorithm.algoproject.mapper;

import com.algorithm.algoproject.dto.CommentDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
public interface BoardCommentMapper {

    void insertBoardComment(CommentDTO commentDTO);
    void updateBoardCommentCnt(int boardNum);
    void updateBoardComment(@Param("c_no") int c_no, @Param("content") String content);
    void deleteBoardComment(int boardNum);


    List<CommentDTO> selectBoardComments(int b_no);
    CommentDTO selectBoardComment(int c_no);

    int selectBoardCommentCount(int b_no);

}
