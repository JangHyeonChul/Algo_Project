package com.algorithm.algoproject.serviceimpl;

import com.algorithm.algoproject.dto.MemberDTO;
import com.algorithm.algoproject.dto.TokenLinkDTO;
import com.algorithm.algoproject.mapper.TokenLinkMapper;
import com.algorithm.algoproject.mapper.UserMapper;
import com.algorithm.algoproject.service.MailSendService;
import com.algorithm.algoproject.service.TokenLinkService;
import com.algorithm.algoproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


@Service
public class TokenLinkServiceImpl implements TokenLinkService {

    TokenLinkMapper tokenLinkMapper;
    MailSendService mailSendService;
    UserMapper userMapper;

    public TokenLinkServiceImpl(TokenLinkMapper tokenLinkMapper, MailSendService mailSendService, UserMapper userMapper) {
        this.tokenLinkMapper = tokenLinkMapper;
        this.mailSendService = mailSendService;
        this.userMapper = userMapper;
    }

    private static final int MINUTES = 1;
    private static final int RESET_TIME = 24;
    private static final int TOKEN_TRIAL = 5;

    @Override
    public TokenLinkDTO getTokenLink(String username) {
        return tokenLinkMapper.selectTokenLink(username);
    }

    @Override
    public void tokenLinkHandler(String username) {
        TokenLinkDTO tokenLinkDTO = tokenLinkMapper.selectTokenLink(username);

        if (Objects.isNull(tokenLinkDTO)) {
            createTokenLink(username);
        }

        if (!Objects.isNull(tokenLinkDTO)) {
            updateTokenLink(username);
        }


        expiryTokenLinkReset();
        mailSendService.sendMailHandler();
    }

    @Override
    public void createToken(String username) {
        TokenLinkDTO tokenLinkDTO = createTokenLinkDTO(username);
        tokenLinkMapper.insertTokenLink(tokenLinkDTO);
    }

    @Override
    public Map<String, String> getTokenData(String username) {
        TokenLinkDTO tokenLink = tokenLinkMapper.selectTokenLink(username);
        MemberDTO userdata = userMapper.findByUserData(username);

        Map<String, String> emailDataMap = new HashMap<>();

        emailDataMap.put("email", userdata.getUser_email());
        emailDataMap.put("role", userdata.getUser_role());
//        emailDataMap.put("resetTime", tokenLink.getT_create_reset().toString());
//        emailDataMap.put("trial", Integer.toString(tokenLink.getT_trial()));

        return emailDataMap;
    }

    private void expiryTokenLinkReset() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        TokenLinkDTO tokenLinkDTO = tokenLinkMapper.selectTokenLink(username);
        LocalDateTime tokenResetTime = tokenLinkDTO.getT_create_reset();
        LocalDateTime nowTime = LocalDateTime.now();

        boolean afterCheck = nowTime.isAfter(tokenResetTime);

        if (afterCheck) {
            LocalDateTime updateResetTime = nowTime.plusMinutes(3);
            tokenLinkMapper.updateTokenLinkReset(username);
            tokenLinkMapper.updateTokenLinkResetTime(username, updateResetTime);

        }
    }

    private void updateTokenLink(String username) {
        TokenLinkDTO tokenLinkDTO = tokenLinkMapper.selectTokenLink(username);
        int tokenTrial = tokenLinkDTO.getT_trial();

        if (tokenTrial < TOKEN_TRIAL) {
            TokenLinkDTO updateTokenLinkDTO = updateTokenLinkDTO(username);
            tokenLinkMapper.updateTokenLinkTrial(username);
            tokenLinkMapper.updateTokenLink(updateTokenLinkDTO);
        }
    }



    private void createTokenLink(String username) {
        TokenLinkDTO tokenLinkDTO = createTokenLinkDTO(username);
        tokenLinkMapper.insertTokenLink(tokenLinkDTO);

    }

    private TokenLinkDTO createTokenLinkDTO(String username) {
        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime plusNowTime = nowTime.plusMinutes(MINUTES);
        LocalDateTime plusResetTime = nowTime.plusMinutes(3);
        String uuid =  UUID.randomUUID().toString();

        return new TokenLinkDTO(uuid, username, nowTime, plusNowTime, plusResetTime);
    }

    private TokenLinkDTO updateTokenLinkDTO(String username) {
        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime plusNowTime = nowTime.plusMinutes(MINUTES);
        String uuid =  UUID.randomUUID().toString();

        return new TokenLinkDTO(uuid, username, nowTime, plusNowTime);

    }
}
