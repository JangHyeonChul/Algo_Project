package com.algorithm.algoproject.serviceimpl;

import com.algorithm.algoproject.config.CompileConstains;
import com.algorithm.algoproject.dto.SolveDTO;
import com.algorithm.algoproject.service.PointService;
import com.algorithm.algoproject.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PointServiceImpl implements PointService {

    @Autowired
    ProblemService problemService;

    @Override
    public void pointServiceHandler(String result, int pageNum) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean resultEqualsSuccess = result.equals(CompileConstains.COMPILE_SUCCESS);
        SolveDTO solveMember = problemService.getSolveMember(username, pageNum);

        System.out.println("solveMember = " + solveMember);

        if (resultEqualsSuccess && solveMember == null) {
            problemService.addPoint(10, username);
            problemService.writeSolveMember(pageNum, username);
        }

    }


    @Override
    public void updateAddPoint(int point, String username) {
        problemService.addPoint(point, username);
    }

    private void addPoint() {

    }

}
