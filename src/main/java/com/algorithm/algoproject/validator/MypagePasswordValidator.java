package com.algorithm.algoproject.validator;


import com.algorithm.algoproject.dto.MemberDTO;
import com.algorithm.algoproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MypagePasswordValidator {

    UserService userService;
    PasswordEncoder passwordEncoder;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public MypagePasswordValidator(UserService userService, PasswordEncoder passwordEncoder, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Map<String, String> myPagePasswordVaild(String originalPwd, String newPassword, String newPasswordCheck) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        MemberDTO userdata = userService.findByUserIdOrEmail(username);
        String userPassword = userdata.getUser_password();

        Map<String, String> passwordErrorsMap = new HashMap<>();

        if (originalPwd.length() < 3) {
            passwordErrorsMap.put("originalPwdError", "비밀번호는 2글자 이상이여야 합니다");
        }

        if (!passwordEncoder.matches(originalPwd, userPassword)) {
            passwordErrorsMap.put("originalPwdError", "현재 비밀번호가 일치하지 않습니다");
        }

        if (newPassword.length() < 3) {
            passwordErrorsMap.put("newPasswordError", "비밀번호는 2글자 이상이여야 합니다");
        }

        if (newPasswordCheck.length() < 3) {
            passwordErrorsMap.put("newPasswordCheckError", "비밀번호는 2글자 이상이여야 합니다");
        }

        if (originalPwd.equals(newPassword)) {
            passwordErrorsMap.put("newPasswordError", "현재 비밀번호와 새로운 비밀번호가 같습니다");
        }

        if (!newPassword.equals(newPasswordCheck)) {
            passwordErrorsMap.put("newPasswordError", "새로운 비밀번호가 서로 일치하지 않습니다");
        }

        if (!passwordErrorsMap.isEmpty()) {
            return passwordErrorsMap;
        }

        String encodePassword = bCryptPasswordEncoder.encode(newPassword);
        userService.updateUserPassword(username, encodePassword);

        return passwordErrorsMap;
    }
}
