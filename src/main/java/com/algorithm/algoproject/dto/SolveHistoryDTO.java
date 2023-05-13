package com.algorithm.algoproject.dto;


import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.Date;


@Data
public class SolveHistoryDTO {

    private int submit_no;
    private String user_id;
    private int p_no;
    private LocalDateTime submit_date;
    private String success_chk;
    private String submit_lang;
    private String submit_fail;

    private String transTime;

    public SolveHistoryDTO() {}

    public SolveHistoryDTO(String user_id, int p_no, String success_chk, String submit_lang, String submit_fail) {
        this.user_id = user_id;
        this.p_no = p_no;
        this.success_chk = success_chk;
        this.submit_lang = submit_lang;
        this.submit_fail = submit_fail;
    }
}
