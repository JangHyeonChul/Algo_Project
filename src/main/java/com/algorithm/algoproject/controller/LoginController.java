package com.algorithm.algoproject.controller;


import com.algorithm.algoproject.dto.LoginDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model) {

        model.addAttribute("loginDTO", new LoginDTO());

        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        SecurityContextHolder.clearContext();

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.removeAttribute("SPRING_SECURITY_CONTEXT");
        }

        return "/problem/problem";
    }
}
