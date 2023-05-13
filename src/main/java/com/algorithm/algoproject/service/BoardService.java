package com.algorithm.algoproject.service;

import com.algorithm.algoproject.PageHandler;
import com.algorithm.algoproject.dto.BoardDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface BoardService {

    void writeBoard(BoardDTO boardDTO);
    void writeBoardViewCnt(int pageNum, HttpServletRequest request, HttpServletResponse response);

    int getCountAllBoard(String category);
    int getBoardOffset(int page, PageHandler pageHandler);

    List<BoardDTO> getAllBoardsByUser(String username);
    List<BoardDTO> getAllBoards(int offset, String category);
    List<BoardDTO> getAllBoardsBySearch(String keyword, int offset);

    BoardDTO getBoard(int pageNum);


}
