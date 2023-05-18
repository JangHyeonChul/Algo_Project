package com.algorithm.algoproject.serviceimpl;

import com.algorithm.algoproject.dto.MemberDTO;
import com.algorithm.algoproject.dto.TokenLinkDTO;
import com.algorithm.algoproject.mapper.TokenLinkMapper;
import com.algorithm.algoproject.mapper.UserMapper;
import com.algorithm.algoproject.service.MailSendService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class MailSendServiceImpl implements MailSendService {

    JavaMailSender javaMailSender;
    UserMapper userMapper;
    TokenLinkMapper tokenLinkMapper;

    public MailSendServiceImpl(JavaMailSender javaMailSender, UserMapper userMapper, TokenLinkMapper tokenLinkMapper) {
        this.javaMailSender = javaMailSender;
        this.userMapper = userMapper;
        this.tokenLinkMapper = tokenLinkMapper;
    }

    @Override
    public void sendMailHandler() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        MemberDTO userdata = userMapper.findByUserData(username);
        TokenLinkDTO tokenLinkUser = tokenLinkMapper.selectTokenLink(username);

        String subject = "메일";
        String from = "wkdgus1136@gmail.com";

        String userEmail = userdata.getUser_email();
        String tokenNum = tokenLinkUser.getT_no();


        StringBuffer sb = new StringBuffer();
        sb.append("회원 가입을 진심으로 축하드립니다<br>");
        sb.append("아래 인증링크를 눌러주신뒤 로그인하면 최종가입이 완료됩니다<br>");
        sb.append("<a href='http://localhost:8080/linkauthentication?&token="
                + tokenNum + "&id=" + userdata.getUser_id() + "' target='_blank'>이메일인증</a>");

        String content = sb.toString();

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mailHelper = new MimeMessageHelper(message, true, "UTF-8");

            mailHelper.setFrom(from);
            mailHelper.setTo(userEmail);
            mailHelper.setSubject(subject);
            mailHelper.setText(content, true);

//            javaMailSender.send(message);
            System.out.println("content = " + content);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

