package com.algorithm.algoproject.service;


import com.algorithm.algoproject.dto.TokenLinkDTO;

import java.time.LocalDateTime;
import java.util.Map;

public interface TokenLinkService {


    void tokenLinkHandler(String username);
    void createToken(String username);

    Map<String, String> getTokenData(String username);
    TokenLinkDTO getTokenLink(String username);
}
