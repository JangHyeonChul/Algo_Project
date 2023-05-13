package com.algorithm.algoproject.mapper;

import com.algorithm.algoproject.dto.TokenLinkDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;

@Mapper
public interface TokenLinkMapper {

    void insertTokenLink(TokenLinkDTO tokenLinkDTO);
    void updateTokenLink(TokenLinkDTO tokenLinkDTO);
    void updateTokenLinkTrial(String username);
    void updateTokenLinkReset(String username);
    void updateTokenLinkResetTime(@Param("username") String username, @Param("resetTime") LocalDateTime resetTime);
    void updateTokenLinkUUID(@Param("username") String username, @Param("uuid") String uuid);

    TokenLinkDTO selectTokenLink(String username);

}
