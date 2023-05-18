package com.algorithm.algoproject.controller;


import com.algorithm.algoproject.dto.*;
import com.algorithm.algoproject.dto.join.AlramBoardDTO;
import com.algorithm.algoproject.service.AlramService;
import com.algorithm.algoproject.service.BoardService;
import com.algorithm.algoproject.service.TokenLinkService;
import com.algorithm.algoproject.service.UserService;
import com.algorithm.algoproject.validator.MypagePasswordValidator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mypage")
public class MypageController {

    UserService userService;
    MypagePasswordValidator mypagePasswordValidator;
    TokenLinkService tokenLinkService;
    BoardService boardService;
    AlramService alramService;

    public MypageController(UserService userService,
                            MypagePasswordValidator mypagePasswordValidator,
                            TokenLinkService tokenLinkService,
                            BoardService boardService,
                            AlramService alramService) {
        this.userService = userService;
        this.mypagePasswordValidator = mypagePasswordValidator;
        this.tokenLinkService = tokenLinkService;
        this.boardService = boardService;
        this.alramService = alramService;
    }

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

        return userService.userModifyInfoValidCheck(username, user, message);
    }

    @GetMapping("/modifyinfo")
    @ResponseBody
    public MemberDTO modifyInfoBtn() {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();

        return userService.findByUserIdOrEmail(user);
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


        return tokenLinkService.getTokenData(username);


    }

    @GetMapping("/email")
    @ResponseBody
    public Map<String, String> emailSubmitBtn() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();


        return tokenLinkService.getTokenData(username);

    }

    @GetMapping("/board")
    @ResponseBody
    public List<BoardDTO> boardSubmitBtn() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();


        return boardService.getAllBoardsByUser(username);
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

        return userService.getUserInfo(username);

    }



}
