package com.algorithm.algoproject.dto;

import lombok.Data;

@Data
public class SiteDTO {

    private int algo_no;
    private String algo_title;
    private String algo_link;
    private String algo_content;

    public SiteDTO() {}
}
