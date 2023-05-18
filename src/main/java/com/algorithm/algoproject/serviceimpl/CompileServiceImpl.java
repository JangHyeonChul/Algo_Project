package com.algorithm.algoproject.serviceimpl;

import com.algorithm.algoproject.compiler.ClangCompile;
import com.algorithm.algoproject.compiler.JavaCompile;
import com.algorithm.algoproject.compiler.PythonCompile;
import com.algorithm.algoproject.config.CompileConstains;
import com.algorithm.algoproject.mapper.ProblemMapper;
import com.algorithm.algoproject.service.CompileService;
import com.algorithm.algoproject.service.HistoryService;
import com.algorithm.algoproject.service.PointService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;


@Service
public class CompileServiceImpl implements CompileService {

    ProblemMapper problemMapper;
    HistoryService historyService;
    PointService pointService;
    JavaCompile javaCompile;
    ClangCompile clangCompile;
    PythonCompile pythonCompile;

    public CompileServiceImpl(ProblemMapper problemMapper,
                              HistoryService historyService,
                              PointService pointService,
                              JavaCompile javaCompile,
                              ClangCompile clangCompile,
                              PythonCompile pythonCompile) {
        this.problemMapper = problemMapper;
        this.historyService = historyService;
        this.pointService = pointService;
        this.javaCompile = javaCompile;
        this.clangCompile = clangCompile;
        this.pythonCompile = pythonCompile;
    }

    @Override
    @Transactional
    public String compileHandler(String code, String lang, Integer pageNum) {
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
