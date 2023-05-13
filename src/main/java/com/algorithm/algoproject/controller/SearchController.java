package com.algorithm.algoproject.controller;


import com.algorithm.algoproject.PageHandler;
import com.algorithm.algoproject.dto.BoardDTO;
import com.algorithm.algoproject.dto.NotificationDTO;
import com.algorithm.algoproject.dto.ProblemDTO;
import com.algorithm.algoproject.service.BoardService;
import com.algorithm.algoproject.service.NotificationService;
import com.algorithm.algoproject.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    ProblemService problemService;

    @Autowired
    BoardService boardService;

    @Autowired
    NotificationService notificationService;

    @GetMapping("/search")
    public String search() {
        return "search";
    }


    @GetMapping("/searching")
    @ResponseBody
    public List problemSearch(@RequestParam("keyword") String keyword,
                              @RequestParam("type") String type) {

        if (type.equals("problem")) {
            List<ProblemDTO> allProblemsBySearch = problemService.getAllProblemsBySearch(keyword, 0);
            return allProblemsBySearch;
        }

        if (type.equals("board")) {
            List<BoardDTO> allBoardsBySearch = boardService.getAllBoardsBySearch(keyword, 0);
            return allBoardsBySearch;
        }

        if (type.equals("noti")) {
            List<NotificationDTO> notificationBoardBySearch = notificationService.getNotificationBoardBySearch(keyword, 0);
            return notificationBoardBySearch;
        }

        return null;
    }
}
