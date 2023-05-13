package com.algorithm.algoproject.controller;


import com.algorithm.algoproject.dto.*;
import com.algorithm.algoproject.dto.join.AlramBoardDTO;
import com.algorithm.algoproject.security.MemberDetails;
import com.algorithm.algoproject.service.AlramService;
import com.algorithm.algoproject.service.BoardService;
import com.algorithm.algoproject.service.TokenLinkService;
import com.algorithm.algoproject.service.UserService;
import com.algorithm.algoproject.validator.MypagePasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mypage")
public class MypageController {

    @Autowired
    UserService userService;

    @Autowired
    MypagePasswordValidator mypagePasswordValidator;

    @Autowired
    TokenLinkService tokenLinkService;

    @Autowired
    BoardService boardService;

    @Autowired
    AlramService alramService;


    @GetMapping("")
    public String mypage(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        MyInfoDTO userInfo = userService.getUserInfo(username);

        model.addAttribute("userInfo", userInfo);

        return "mypage";
    }

    @PostMapping("/modifyinfo")
    @ResponseBody
    public String modifyInfo(@RequestParam("username") String username,
                           @RequestParam("message") String message) {

        String user = SecurityContextHolder.getContext().getAuthentication().getName();

        String userInfoValid = userService.userModifyInfoValidCheck(username, user, message);

        return userInfoValid;
    }

    @GetMapping("/modifyinfo")
    @ResponseBody
    public MemberDTO modifyInfoBtn() {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        MemberDTO userdata = userService.findByUserIdOrEmail(user);

        return userdata;
    }

    @PostMapping("/password")
    @ResponseBody
    public Map<String, String> passwordChange(@RequestParam("originalPwd") String originalPwd,
                               @RequestParam("newPassword") String newPassword,
                               @RequestParam("newPasswordCheck") String newPasswordCheck) {

        return mypagePasswordValidator.myPagePasswordVaild(originalPwd, newPassword, newPasswordCheck);
    }

    @PostMapping("/email")
    @ResponseBody
    public Map<String, String> emailSubmit() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        tokenLinkService.tokenLinkHandler(username);
        Map<String, String> tokenData = tokenLinkService.getTokenData(username);



        return tokenData;


    }

    @GetMapping("/email")
    @ResponseBody
    public Map<String, String> emailSubmitBtn() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, String> tokenData = tokenLinkService.getTokenData(username);


        return tokenData;

    }

    @GetMapping("/board")
    @ResponseBody
    public List<BoardDTO> boardSubmitBtn() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<BoardDTO> allBoardsByUser = boardService.getAllBoardsByUser(username);


        return allBoardsByUser;
    }

    @GetMapping("/alram")
    @ResponseBody
    public List<AlramBoardDTO> alramSubmitBtn() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<AlramBoardDTO> boardAlrams = alramService.getBoardAlrams(username);

        for (AlramBoardDTO alramBoardDTO : boardAlrams) {
            System.out.println("alramBoardDTO = " + alramBoardDTO.getA_no());
        }

        return boardAlrams;
    }


    @GetMapping("/userinfo")
    @ResponseBody
    public MyInfoDTO userInfoSubmitBtn() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        MyInfoDTO userInfo = userService.getUserInfo(username);

        return userInfo;

    }



}
