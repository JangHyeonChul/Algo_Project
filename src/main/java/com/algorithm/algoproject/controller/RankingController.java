package com.algorithm.algoproject.controller;

import com.algorithm.algoproject.dto.RankDTO;
import com.algorithm.algoproject.service.RankService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RankingController {

    RankService rankService;

    public RankingController(RankService rankService) {
        this.rankService = rankService;
    }

    @GetMapping("/rank")
    public String rank(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<RankDTO> rankInfos = rankService.getRankInfo();
        RankDTO userRankInfo = rankService.getRankInfoByUserName(username);


        model.addAttribute("rankInfos", rankInfos);
        model.addAttribute("userRankInfo", userRankInfo);


        return "ranking";
    }


}
