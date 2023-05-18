package com.algorithm.algoproject.controller.admin;


import com.algorithm.algoproject.dto.NotificationDTO;
import com.algorithm.algoproject.service.NotificationService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminNotificationController {

    NotificationService notificationService;

    public AdminNotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/notification")
    public String notification(@ModelAttribute("notificationDTO") NotificationDTO notificationDTO) {



        return "/admin/admin-notification";
    }

    @PostMapping("/notification")
    public String notificationSubmit(NotificationDTO notificationDTO) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        notificationDTO.setUser_id(username);
        notificationService.writeNotification(notificationDTO);


        return "redirect:/notification/0";
    }



}
