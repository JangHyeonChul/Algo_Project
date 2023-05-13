package com.algorithm.algoproject.controller;

import com.algorithm.algoproject.dto.MemberDTO;
import com.algorithm.algoproject.service.TokenLinkService;
import com.algorithm.algoproject.service.UserService;
import com.algorithm.algoproject.validator.RegisterValidaor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {

    @Autowired
    UserService userService;

    @Autowired
    RegisterValidaor registerValidaor;

    @Autowired
    TokenLinkService tokenLinkService;


    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("memberDTO", new MemberDTO());

        return "register";
    }

    @GetMapping("/welcome")
    public String welcome() {

        return "welcome";
    }

    @PostMapping("/register")
    public String signup(@ModelAttribute MemberDTO memberDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        registerValidaor.validate(memberDTO, bindingResult);

        if(bindingResult.hasErrors()) {
            return "register";
        }

        userService.registerUser(memberDTO);


        redirectAttributes.addFlashAttribute("welcomeMessage", "RegisterSuccess");

        return "redirect:/welcome";
    }
}
