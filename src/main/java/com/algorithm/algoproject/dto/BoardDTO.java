package com.algorithm.algoproject.dto;

import com.algorithm.algoproject.Time;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class BoardDTO {

    private int b_no;
    private String user_id;
    private String b_title;
    private String b_category;
    private int b_comment_cnt;
    private int b_recommdation;
    private LocalDateTime b_create;
    private String b_content;
    private String b_lang;
    private String b_comment_active;
    private int b_viewcnt;

    private String b_transCreate;



    public BoardDTO(String user_id, String b_title, String b_category, String b_content, String b_lang, String b_comment_active) {
        this.user_id = user_id;
        this.b_title = b_title;
        this.b_category = b_category;
        this.b_content = b_content;
        this.b_lang = b_lang;
        this.b_comment_active = b_comment_active;
    }

    public BoardDTO() {}

}
