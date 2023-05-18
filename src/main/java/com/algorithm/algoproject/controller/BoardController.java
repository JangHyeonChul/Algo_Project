package com.algorithm.algoproject.controller;


import com.algorithm.algoproject.PageHandler;
import com.algorithm.algoproject.dto.*;
import com.algorithm.algoproject.service.AlramService;
import com.algorithm.algoproject.service.BoardCommentService;
import com.algorithm.algoproject.service.BoardService;
import com.algorithm.algoproject.service.RecommdationService;
import com.algorithm.algoproject.validator.BoardValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/board")
public class BoardController {


    BoardValidator boardValidator;
    BoardService boardService;
    BoardCommentService boardCommentService;
    RecommdationService recommdationService;
    AlramService alramService;

    public BoardController(BoardValidator boardValidator,
                           BoardService boardService,
                           BoardCommentService boardCommentService,
                           RecommdationService recommdationService,
                           AlramService alramService) {
        this.boardValidator = boardValidator;
        this.boardService = boardService;
        this.boardCommentService = boardCommentService;
        this.recommdationService = recommdationService;
        this.alramService = alramService;
    }

    @GetMapping("")
    public String board(@RequestParam(defaultValue = "1") Integer page, Model model) {
        return getBoard(page, null, model);

    }

    @GetMapping("/type/{category}")
    public String boardCategoryQU(@PathVariable String category,
                                  @RequestParam(defaultValue = "1") Integer page, Model model) {

        return getBoard(page, category, model);
    }



    @GetMapping("/write")
    public String writeBoardPage(Model model) {

        model.addAttribute("boardDTO", new BoardDTO());

        return "/board/board-write";
    }

    @GetMapping("/page/{boardNum}")
    @Transactional
    public String readBoard(@PathVariable("boardNum")int boardNumber,
                            @RequestParam(defaultValue = "1") int page, HttpServletRequest request, HttpServletResponse response, Model model ) {


        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        alramService.updateBoardAlram(boardNumber);
        boardService.writeBoardViewCnt(boardNumber, request, response);
        BoardDTO board = boardService.getBoard(boardNumber);

        if (board.getB_delete() == 1) {
            return "redirect:/board";
        }

        List<CommentDTO> boardComments = boardCommentService.getBoardComments(boardNumber);

        int boardTotalCount = boardService.getCountAllBoard(null);
        PageHandler pageHandler = new PageHandler(boardTotalCount, page);

        int offset = boardService.getBoardOffset(page, pageHandler);
        List<BoardDTO> allBoards = boardService.getAllBoards(offset, null);

        model.addAttribute("page", page);
        model.addAttribute("offset", offset);
        model.addAttribute("boards", allBoards);
        model.addAttribute("ph", pageHandler);

        model.addAttribute("username", username);
        model.addAttribute("page", page);
        model.addAttribute("boardComments", boardComments);
        model.addAttribute("board", board);
        model.addAttribute("boardNumber", boardNumber);

        return "/board/board-info";
    }

    @DeleteMapping("/page/{pageNum}")
    @ResponseBody
    @Transactional
    public boolean deleteBoard(@PathVariable("pageNum") int pageNum) {

        if (!boardValidator.boardDeleteVaildate(pageNum)) {
            return false;
        }

        boardCommentService.deleteBoardComment(pageNum);
        boardService.deleteBoard(pageNum);

        return true;
    }

    @GetMapping("/update/{pageNum}")
    public String modifyBoard(@PathVariable("pageNum") int pageNum, Model model) {

        BoardDTO board = boardService.getBoard(pageNum);
        if (!boardValidator.boardDeleteVaildate(pageNum) || board.getB_delete() == 1) {
            return "redirect:/board";
        }

        model.addAttribute("board", board);
        return "/board/board-modify";

    }

    @PatchMapping("/update/{pageNum}")
    public String modifyBoard(@PathVariable("pageNum") int pageNum,
                              @ModelAttribute("board") BoardDTO board) {

        if (!boardValidator.boardDeleteVaildate(pageNum)) {
            return "redirect:/board";
        }

        boardService.modifyBoard(pageNum, board);

        return "redirect:/board";

    }



    @PostMapping("/write")
    public String postingBoard(@ModelAttribute BoardDTO boardDTO, BindingResult bindingResult) {
        boardValidator.validate(boardDTO, bindingResult);

        if(bindingResult.hasErrors()) {
            return "/board/board-write";
        }

        boardService.writeBoard(boardDTO);
        return "redirect:/board";
    }

    @PostMapping("/comment")
    @ResponseBody
    @Transactional
    public List<CommentDTO> boardComment(@RequestParam("boardNumber") int boardNumber,
                                    @RequestParam("commentContent") String commentContent) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        BoardDTO board = boardService.getBoard(boardNumber);
        boardCommentService.writeBoardComment(boardNumber, commentContent);
        List<CommentDTO> boardComments = boardCommentService.getBoardComments(boardNumber);
        boardCommentService.updateBoardCommentCnt(boardNumber);

        String userId = board.getUser_id();
        AlramDTO alramDTO = new AlramDTO(boardNumber, userId);
        alramService.writeBoardAlram(alramDTO);

        for (CommentDTO commentDTO : boardComments) {
            String commentUserName = commentDTO.getUser_id();
            if (commentUserName.equals(username)) {
                commentDTO.setShowModify(true);
            }
        }

        return boardComments;
    }

    @DeleteMapping("/comment")
    @ResponseBody
    @Transactional
    public List<CommentDTO> boardCommentDelete(@RequestParam("commentNumber") int c_no,
                                   @RequestParam("boardNumber") int b_no) {

        boolean boardCommentValid = boardValidator.boardCommentVaildate(c_no);

        if (!boardCommentValid) {
            return null;
        }

        boardCommentService.deleteBoardComment(c_no);
        return  boardCommentService.getBoardComments(b_no);
    }

    @GetMapping("/comment/update")
    @ResponseBody
    public CommentDTO boardCommentModify(@RequestParam("commentNumber") int c_no) {

        boolean boardCommentValid = boardValidator.boardCommentVaildate(c_no);

        if (!boardCommentValid) {
            return null;
        }

        return boardCommentService.getBoardComment(c_no);

    }

    @PatchMapping("/comment/update")
    @ResponseBody
    public boolean boardCommentModify(@RequestParam("commentNumber") int c_no,
                                         @RequestParam("commentContent") String content) {

        boolean boardCommentValid = boardValidator.boardCommentVaildate(c_no);

        if (!boardCommentValid) {
            return false;
        }


        boardCommentService.updateBoardComment(c_no, content);

        return true;

    }

    @PostMapping("/recommdation")
    @ResponseBody
    @Transactional
    public RecommdationDTO boardRecommdation(@RequestParam("boardNumber") int boardNumber) {

        return recommdationService.writeBoardRecommdation(boardNumber);

    }



    private String getBoard(Integer page, String category, Model model) {
        int boardTotalCount = boardService.getCountAllBoard(category);
        PageHandler pageHandler = new PageHandler(boardTotalCount, page);

        int offset = boardService.getBoardOffset(page, pageHandler);
        List<BoardDTO> allBoards = boardService.getAllBoards(offset, category);

        model.addAttribute("page", page);
        model.addAttribute("offset", offset);
        model.addAttribute("boards", allBoards);
        model.addAttribute("ph", pageHandler);

        return "/board/board";
    }


}
