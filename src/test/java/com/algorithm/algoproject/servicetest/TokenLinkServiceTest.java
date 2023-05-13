package com.algorithm.algoproject.servicetest;


import com.algorithm.algoproject.Time;
import com.algorithm.algoproject.dto.MemberDTO;
import com.algorithm.algoproject.dto.SolveHistoryDTO;
import com.algorithm.algoproject.dto.TokenLinkDTO;
import com.algorithm.algoproject.mapper.ProblemMapper;
import com.algorithm.algoproject.service.HistoryService;
import com.algorithm.algoproject.service.ProblemService;
import com.algorithm.algoproject.service.TokenLinkService;
import com.algorithm.algoproject.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class TokenLinkServiceTest {

    @Autowired
    TokenLinkService tokenLinkService;

    @Autowired
    HistoryService historyService;

    @Autowired
    ProblemMapper problemMapper;



    @Test
    void createTokenLinkTest() {
        LocalDateTime nowTime = LocalDateTime.now();
        String nowTimeFormat = nowTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
        LocalDateTime plusNowTime = nowTime.plusMinutes(1);
        String plusNowTimeFormat = plusNowTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));

        String uuid =  UUID.randomUUID().toString();
        TokenLinkDTO token = new TokenLinkDTO(uuid, "wkdgus1136", nowTime, plusNowTime);

        System.out.println("uuid = " + uuid);


//        tokenLinkService.createTokenLink(token);
        TokenLinkDTO tokenLinkDTO = tokenLinkService.getTokenLink("wkdgus1136");

        LocalDateTime tCreate = tokenLinkDTO.getT_create();
        LocalDateTime tCreateExpiry = tokenLinkDTO.getT_create_expiry();

        boolean after = tCreateExpiry.isAfter(nowTime);

        System.out.println("after = " + after);
        System.out.println("tokenLinkDTO = " + tokenLinkDTO);

    }

    @Test
    public void timetest() {
        List<SolveHistoryDTO> solveHistoryDTOS = problemMapper.selectSolveHistorys("wkdgus1123", 1);

        for(SolveHistoryDTO solveHistoryDTO : solveHistoryDTOS ) {
            System.out.println("solveHistoryDTO = " + solveHistoryDTO);
        }



    }
}
