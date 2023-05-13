package com.algorithm.algoproject.dto;


import lombok.Data;

@Data
public class ProblemOptionDTO {

    private String use;
    private String order;
    private String category;
    private String level;
    private String lang;
    private String etc;

    public ProblemOptionDTO() {}


    public ProblemOptionDTO(String use, String order, String category, String level, String lang, String etc) {
        if (lang.equals("KR")) {
            this.lang = "한국어";
        }

        if (lang.equals("ENG")) {
            this.lang = "영어";
        }

        if (lang.equals("total")) {
            this.lang = "total";
        }

        this.category = category;
        this.use = use;
        this.order = order;
        this.level = level;
        this.etc = etc;
    }

}
