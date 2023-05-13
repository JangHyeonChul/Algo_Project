package com.algorithm.algoproject.dto;


import lombok.Data;

@Data
public class RecommdationDTO {

    private int recom_no;
    private String user_id;
    private int b_no;
    private boolean insertCheck;
    private int recommdationCnt;

    RecommdationDTO() {}

}
