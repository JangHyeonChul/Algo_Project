package com.algorithm.algoproject.controller;


import com.algorithm.algoproject.config.AuthorityConstains;
import com.algorithm.algoproject.dto.TokenLinkDTO;
import com.algorithm.algoproject.service.TokenLinkService;
import com.algorithm.algoproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class TokenLinkController {

    TokenLinkService tokenLinkService;
    UserService userService;

    public TokenLinkController(TokenLinkService tokenLinkService, UserService userService) {
        this.tokenLinkService = tokenLinkService;
        this.userService = userService;
    }

    @GetMapping("/linkauthentication")
    public String linkAuthentication(@RequestParam("token") String token,
                                     @RequestParam("id") String id,
                                     Model model) {

        TokenLinkDTO tokenLink = tokenLinkService.getTokenLink(id);
        LocalDateTime timeNow = LocalDateTime.now();
        LocalDateTime tokenCreateExpiry = tokenLink.getT_create_expiry();
        String roleUser = AuthorityConstains.ROLE_USER;


        boolean after = tokenCreateExpiry.isAfter(timeNow);

        if(after) {
            userService.updateUserAuthGrade(id, roleUser);
            model.addAttribute("welcomeMessage", "LinkSuccess");
            System.out.println("인증에 성공하였습니다");
        } else {
            model.addAttribute("welcomeMessage", "LinkFail");
            System.out.println("토큰이 만료되었습니다");
        }

        return "welcome";

    }
}
