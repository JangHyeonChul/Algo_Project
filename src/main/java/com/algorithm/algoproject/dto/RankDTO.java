package com.algorithm.algoproject.dto;


import lombok.Data;

@Data
public class RankDTO {

    private int user_rank;
    private String username;
    private String message;
    private int problem_count;
    private int point;

    public RankDTO() {}

}
