package com.algorithm.algoproject.service;


import com.algorithm.algoproject.dto.ProblemDTO;

import java.util.ArrayList;
import java.util.List;

public interface CompileService {

    String compileHandler(String code, String lang, Integer pageNum);


}
