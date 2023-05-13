package com.algorithm.algoproject.dto;

import lombok.Data;

@Data
public class LoginDTO {

    private String username;
    private String password;
    private boolean idsave;

    public LoginDTO() {}

}
