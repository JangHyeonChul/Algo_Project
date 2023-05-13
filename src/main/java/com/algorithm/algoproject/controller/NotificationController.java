package com.algorithm.algoproject.controller;


import com.algorithm.algoproject.PageHandler;
import com.algorithm.algoproject.dto.BoardDTO;
import com.algorithm.algoproject.dto.CommentDTO;
import com.algorithm.algoproject.dto.NotificationDTO;
import com.algorithm.algoproject.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @GetMapping("/notification/{pageNum}")
    public String notification(@PathVariable(required = false) int pageNum,
                               @RequestParam(defaultValue = "1") int page, Model model) {

        if (pageNum == 0) {
            int lastNum = notificationService.getNotificationCounts();
            pageNum = lastNum;
        }

        NotificationDTO notificationBoardByPageNum = notificationService.getNotificationBoardByPageNum(pageNum);

        int notificationCounts = notificationService.getNotificationCounts();
        PageHandler pageHandler = new PageHandler(notificationCounts, page);

        int offset = notificationService.getNotificationOffset(page, pageHandler);
        List<NotificationDTO> notificationBoard = notificationService.getNotificationBoard(offset);


        model.addAttribute("notificationBoard", notificationBoardByPageNum);
        model.addAttribute("page", page);
        model.addAttribute("offset", offset);
        model.addAttribute("notification", notificationBoard);
        model.addAttribute("ph", pageHandler);

        return "notification";
    }
}
