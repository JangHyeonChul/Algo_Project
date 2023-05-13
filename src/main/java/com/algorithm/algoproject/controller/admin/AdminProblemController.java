package com.algorithm.algoproject.controller.admin;


import com.algorithm.algoproject.dto.AnswerDTO;
import com.algorithm.algoproject.dto.MemberDTO;
import com.algorithm.algoproject.dto.ProblemDTO;
import com.algorithm.algoproject.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@Controller
@RequestMapping("/admin")
public class AdminProblemController {


    @Autowired
    ProblemService problemService;

    @GetMapping("/problem")
    public String admin(Model model) {

        model.addAttribute("problemDTO", new ProblemDTO());
        model.addAttribute("answerDTO", new AnswerDTO());

        return "admin/admin-problem";
    }

    @PostMapping("/problem")
    @Transactional
    public String adminproblem(@ModelAttribute ProblemDTO problemDTO,
                               @RequestParam("inputs[]") ArrayList<String> inputs,
                               @RequestParam("outputs[]") ArrayList<String> outputs) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        problemDTO.setUser_id(name);

        problemService.writeProblem(problemDTO);
        int p_no = problemService.getLastInsertID();
        problemService.writeProblemAnswer(inputs, outputs, p_no);



        return "redirect:/admin/problem";
    }
}
