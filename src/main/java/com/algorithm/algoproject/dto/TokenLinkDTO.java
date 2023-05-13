package com.algorithm.algoproject.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TokenLinkDTO {

    private String t_no;
    private String user_id;
    private LocalDateTime t_create;
    private LocalDateTime t_create_expiry;
    private int t_trial;
    private LocalDateTime t_create_reset;

    public TokenLinkDTO() {}

    public TokenLinkDTO(String t_no, String user_id, LocalDateTime t_create, LocalDateTime t_create_expiry, LocalDateTime t_create_reset) {
        this.t_no = t_no;
        this.user_id = user_id;
        this.t_create = t_create;
        this.t_create_expiry = t_create_expiry;
        this.t_create_reset = t_create_reset;
    }

    public TokenLinkDTO(String t_no, String user_id, LocalDateTime t_create, LocalDateTime t_create_expiry) {
        this.t_no = t_no;
        this.user_id = user_id;
        this.t_create = t_create;
        this.t_create_expiry = t_create_expiry;
    }
}
