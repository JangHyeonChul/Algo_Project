package com.algorithm.algoproject.validator;

import com.algorithm.algoproject.dto.BoardDTO;
import com.algorithm.algoproject.dto.CommentDTO;
import com.algorithm.algoproject.service.BoardCommentService;
import com.algorithm.algoproject.service.BoardService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BoardValidator implements Validator {

    BoardService boardService;
    BoardCommentService boardCommentService;

    public BoardValidator(BoardService boardService, BoardCommentService boardCommentService) {
        this.boardService = boardService;
        this.boardCommentService = boardCommentService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return BoardDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BoardDTO boardDTO = (BoardDTO) target;
        String boardTitle = boardDTO.getB_title();
        String boardContent = boardDTO.getB_content();

        if(!StringUtils.hasText(boardTitle)) {
            errors.rejectValue("b_title", "required");
        }

        if(boardTitle.length() > 250) {
            errors.rejectValue("b_title", "overlength");
        }

        if(!StringUtils.hasText(boardDTO.getB_content())) {
            errors.rejectValue("b_content", "required");
        }

        if(boardContent.length() < 10) {
            errors.rejectValue("b_content", "length");
        }
    }

    public boolean boardDeleteVaildate(int pageNum) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        BoardDTO board = boardService.getBoard(pageNum);
        String boardUserName = board.getUser_id();

        if (!username.equals(boardUserName)) {
            return false;
        }


        return true;
    }

    public boolean boardCommentVaildate(int c_no) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        CommentDTO boardComment = boardCommentService.getBoardComment(c_no);

        if (!boardComment.getUser_id().equals(username)) {
            return false;
        }

        return true;
    }
}
