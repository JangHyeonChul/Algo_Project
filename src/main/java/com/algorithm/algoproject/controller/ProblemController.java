package com.algorithm.algoproject.controller;

import com.algorithm.algoproject.PageHandler;
import com.algorithm.algoproject.dto.*;
import com.algorithm.algoproject.service.CompileService;
import com.algorithm.algoproject.service.HistoryService;
import com.algorithm.algoproject.service.ProblemService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;


@Controller
@Slf4j
public class ProblemController {

    @Autowired
    ProblemService problemService;

    @Autowired
    CompileService compileService;

    @Autowired
    HistoryService historyService;



    @GetMapping("/problem")
    public String problem(@RequestParam(defaultValue = "1") Integer page,
                          @RequestParam(defaultValue = "unused") String use,
                          @RequestParam(defaultValue = "desc") String order,
                          @RequestParam(defaultValue = "total") String type,
                          @RequestParam(defaultValue = "total") String level,
                          @RequestParam(defaultValue = "total") String lang,
                          @RequestParam(defaultValue = "total") String etc,
                          Model model) {

        ProblemOptionDTO problemOptionDTO = new ProblemOptionDTO(use, order, type, level, lang, etc);
        int problemTotalCountByType = problemService.getCountAllProblemsByOption(problemOptionDTO);
        PageHandler pageHandler = new PageHandler(problemTotalCountByType, page);
        int offset = problemService.getPageOffset(page, pageHandler);
        List<ProblemDTO> allProblems =  problemService.getAllProblemsByOptions(problemOptionDTO, offset);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<SolveDTO> allSolveMember = problemService.getAllSolveMember(username);

        model.addAttribute("option", problemOptionDTO);
        model.addAttribute("solvenums", allSolveMember);
        model.addAttribute("offset", offset);
        model.addAttribute("problems", allProblems);
        model.addAttribute("ph", pageHandler);

        return "/problem/problem";
    }



    @GetMapping("/problem/{pageNum}")
    public String problemPage(@PathVariable Integer pageNum, @RequestParam(defaultValue = "1") Integer page, Model model) {
        ProblemDTO problem = problemService .getProblem(pageNum);
        List<AnswerDTO> problemAnswers = problemService.getProblemAnswers(pageNum);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<SolveDTO> allSolveMember = problemService.getAllSolveMember(username);

        model.addAttribute("problem", problem);
        model.addAttribute("page", page);
        model.addAttribute("problemAnswer", problemAnswers);
        model.addAttribute("solvenums", allSolveMember);

        return "/problem/problem-Info";
    }




    @GetMapping("/challenge/{pageNum}")
    public String problemChallenge(@PathVariable Integer pageNum, @RequestParam(defaultValue = "1") Integer page, Model model) {
        ProblemDTO problem = problemService.getProblem(pageNum);
        List<AnswerDTO> problemAnswers = problemService.getProblemAnswers(pageNum);

        model.addAttribute("problem", problem);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("page", page);
        model.addAttribute("problemAnswer", problemAnswers);


        return "/problem/problem-submit";
    }

    @GetMapping("/history/{pageNum}")
    public String problemHistory(@PathVariable Integer pageNum, @RequestParam(defaultValue = "1") Integer page, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        List<SolveHistoryDTO> solveHistorys = historyService.getSolveHistorys(username, pageNum);


        model.addAttribute("solveHistoryList", solveHistorys);
        model.addAttribute("page", page);

        return "/problem/problem-history";
    }




    @PostMapping("/challenge/{pageNum}")
        public String challenge(String code, @RequestParam("language")String lang, @PathVariable Integer pageNum, RedirectAttributes redirectAttributes) {
        String compileResult = compileService.compileHandler(code, lang, pageNum);

        return "redirect:/problem";
    }

    @GetMapping("/random")
    public String random() {
        int countAllProblem = problemService.getCountAllProblem();
        int randomPage = problemService.getRandomPage(countAllProblem);

        return "redirect:/problem/" + randomPage ;

    }

    @GetMapping("/problem/type")
    public String type(Model model) {
        Map<String, Integer> problemTypeCount = problemService.getProblemTypeCountMap();

        model.addAttribute("problemTypeCount", problemTypeCount);

        return "/problem/problem-type";
    }

    @GetMapping("/problem/type/{typename}")
    public String typeproblem(@PathVariable("typename") String typename,
                              @RequestParam(defaultValue = "1") Integer page, Model model) {

        int problemTotalCountByType = problemService.getProblemTypeCount(typename);
        PageHandler pageHandler = new PageHandler(problemTotalCountByType, page);

        int offset = problemService.getPageOffset(page, pageHandler);
        List<ProblemDTO> allProblems =  problemService.getAllProblemsByType(typename, offset);

        return problemsTypeHandler(offset, allProblems, pageHandler, model);
    }




    private String problemsTypeHandler(int offset, List<ProblemDTO> allProblems, PageHandler pageHandler, Model model) {

        ProblemOptionDTO problemOptionDTO = new ProblemOptionDTO();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<SolveDTO> allSolveMember = problemService.getAllSolveMember(username);

        model.addAttribute("solvenums", allSolveMember);
        model.addAttribute("problemOptionDTO", problemOptionDTO);
        model.addAttribute("offset", offset);
        model.addAttribute("problems", allProblems);
        model.addAttribute("ph", pageHandler);

        return "/problem/problem";
    }
}
