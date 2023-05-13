package com.algorithm.algoproject.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MyInfoDTO {


    private int successProblem;
    private int failProblem;
    private int submitProblem;
    private int point;
    private String grade;
    private String user_role;
    private String email;
    private String create_at;


}
