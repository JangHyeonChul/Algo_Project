package com.algorithm.algoproject.controller;


import com.algorithm.algoproject.PageHandler;
import com.algorithm.algoproject.dto.*;
import com.algorithm.algoproject.service.AlramService;
import com.algorithm.algoproject.service.BoardCommentService;
import com.algorithm.algoproject.service.BoardService;
import com.algorithm.algoproject.service.RecommdationService;
import com.algorithm.algoproject.validator.BoardValidator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    BoardValidator boardValidator;

    @Autowired
    BoardService boardService;

    @Autowired
    BoardCommentService boardCommentService;

    @Autowired
    RecommdationService recommdationService;

    @Autowired
    AlramService alramService;



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
        alramService.updateBoardAlram(boardNumber);
        boardService.writeBoardViewCnt(boardNumber, request, response);
        BoardDTO board = boardService.getBoard(boardNumber);
        List<CommentDTO> boardComments = boardCommentService.getBoardComments(boardNumber);

        int boardTotalCount = boardService.getCountAllBoard(null);
        PageHandler pageHandler = new PageHandler(boardTotalCount, page);

        int offset = boardService.getBoardOffset(page, pageHandler);
        List<BoardDTO> allBoards = boardService.getAllBoards(offset, null);

        model.addAttribute("page", page);
        model.addAttribute("offset", offset);
        model.addAttribute("boards", allBoards);
        model.addAttribute("ph", pageHandler);

        model.addAttribute("page", page);
        model.addAttribute("boardComments", boardComments);
        model.addAttribute("board", board);
        model.addAttribute("boardNumber", boardNumber);


        return "/board/board-info";
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

        BoardDTO board = boardService.getBoard(boardNumber);
        boardCommentService.writeBoardComment(boardNumber, commentContent);
        List<CommentDTO> boardComments = boardCommentService.getBoardComments(boardNumber);
        boardCommentService.updateBoardCommentCnt(boardNumber);

        String userId = board.getUser_id();
        AlramDTO alramDTO = new AlramDTO(boardNumber, userId);
        alramService.writeBoardAlram(alramDTO);

        return boardComments;
    }

    @PostMapping("/recommdation")
    @ResponseBody
    @Transactional
    public RecommdationDTO boardRecommdation(@RequestParam("boardNumber") int boardNumber) {

        RecommdationDTO recommdationDTO = recommdationService.writeBoardRecommdation(boardNumber);
        return recommdationDTO;

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
