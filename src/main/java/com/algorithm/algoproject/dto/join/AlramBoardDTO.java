package com.algorithm.algoproject.dto.join;

import lombok.Data;

@Data
public class AlramBoardDTO {

    private int a_no;
    private int b_no;
    private String receive_user_id;
    private String a_check;
    private String b_title;
    private String b_category;

    AlramBoardDTO() {}
}
