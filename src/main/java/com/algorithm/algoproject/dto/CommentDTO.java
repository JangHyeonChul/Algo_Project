package com.algorithm.algoproject.dto;


import com.algorithm.algoproject.Time;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class CommentDTO {

    private int b_no;
    private String user_id;
    private String c_comment;
    private LocalDateTime c_create;
    private int c_recommadation;

    private String c_transCreate;

    public CommentDTO() {}


}
