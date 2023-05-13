package com.algorithm.algoproject.serviceimpl;

import com.algorithm.algoproject.compiler.ClangCompile;
import com.algorithm.algoproject.compiler.JavaCompile;
import com.algorithm.algoproject.compiler.PythonCompile;
import com.algorithm.algoproject.config.CompileConstains;
import com.algorithm.algoproject.dto.ProblemDTO;
import com.algorithm.algoproject.service.CompileService;
import com.algorithm.algoproject.service.HistoryService;
import com.algorithm.algoproject.service.PointService;
import com.algorithm.algoproject.service.ProblemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;


@Service
public class CompileServiceImpl implements CompileService {

    @Autowired
    ProblemService problemService;

    @Autowired
    HistoryService historyService;

    @Autowired
    PointService pointService;

    @Autowired
    JavaCompile javaCompile;

    @Autowired
    ClangCompile clangCompile;

    @Autowired
    PythonCompile pythonCompile;




    @Override
    @Transactional
    public String compileHandler(String code, String lang, Integer pageNum) {
        ProblemDTO problem = problemService.getProblem(pageNum);
        List<String> compileResults = new ArrayList<>();

        if (lang.equals("JAVA")) {
            compileResults = javaCompile.compileJavaCode(code, pageNum);
        }

        if (lang.equals("C")) {
            compileResults = clangCompile.compileClangCode(code, pageNum);
        }

        if (lang.equals("PYTHON")) {
            compileResults = pythonCompile.compilePythonCode(code, pageNum);
        }


        String compileResult = compileResultHandler(compileResults);
        historyService.madeBySolveHistory(compileResult, lang, pageNum);
        pointService.pointServiceHandler(compileResult, pageNum);


        return null;
    }

    private String compileResultHandler(List<String> compileResults) {


        for(String compileResult : compileResults) {
            if(compileResult.equals(CompileConstains.COMPILE_ERROR)) {
                return CompileConstains.COMPILE_ERROR;
            }
        }

        for(String compileResult : compileResults) {
            if(compileResult.equals(CompileConstains.COMPILE_FAIL))
                return CompileConstains.COMPILE_FAIL;
        }

        return CompileConstains.COMPILE_SUCCESS;

    }
}
