package com.algorithm.algoproject.serviceimpl;

import com.algorithm.algoproject.Time;
import com.algorithm.algoproject.dto.CommentDTO;
import com.algorithm.algoproject.mapper.BoardCommentMapper;
import com.algorithm.algoproject.service.BoardCommentService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BoardCommentServiceImpl implements BoardCommentService {

    BoardCommentMapper boardCommentMapper;

    public BoardCommentServiceImpl(BoardCommentMapper boardCommentMapper) {
        this.boardCommentMapper = boardCommentMapper;
    }

    @Override
    public void writeBoardComment(int boardNumber, String commentContent) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setB_no(boardNumber);
        commentDTO.setUser_id(username);
        commentDTO.setC_comment(commentContent);

        boardCommentMapper.insertBoardComment(commentDTO);
    }

    @Override
    public void updateBoardCommentCnt(int b_no) {
        boardCommentMapper.updateBoardCommentCnt(b_no);
    }

    @Override
    public void deleteBoardComment(int pageNum) {
        boardCommentMapper.deleteBoardComment(pageNum);
    }

    @Override
    public void updateBoardComment(int c_no, String content) {
        boardCommentMapper.updateBoardComment(c_no, content);
    }

    @Override
    public List<CommentDTO> getBoardComments(int b_no) {
        List<CommentDTO> commentDTOS = boardCommentMapper.selectBoardComments(b_no);

        for(CommentDTO commentDTO : commentDTOS) {
            LocalDateTime cCreate = commentDTO.getC_create();
            String commentTime = Time.txtDate(cCreate);
            commentDTO.setC_transCreate(commentTime);
        }

        return commentDTOS;


    }

    @Override
    public CommentDTO getBoardComment(int c_no) {
        return boardCommentMapper.selectBoardComment(c_no);
    }

    @Override
    public int getBoardCommentCount(int b_no) {
        return boardCommentMapper.selectBoardCommentCount(b_no);
    }
}
