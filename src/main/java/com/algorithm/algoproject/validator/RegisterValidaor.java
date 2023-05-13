package com.algorithm.algoproject.validator;

import com.algorithm.algoproject.config.RegisterConstains;
import com.algorithm.algoproject.dto.MemberDTO;
import com.algorithm.algoproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class RegisterValidaor implements Validator {

    @Autowired
    UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return MemberDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MemberDTO memberDTO = (MemberDTO) target;

        MemberDTO userFindbyEmail = userService.findByUserIdOrEmail(memberDTO.getUser_email());
        MemberDTO userFindbyId = userService.findByUserIdOrEmail(memberDTO.getUser_id());
        MemberDTO userfindbyNickname = userService.findByUserIdOrEmail(memberDTO.getUser_nickname());

        if(!StringUtils.hasText(memberDTO.getUser_email())) {
            errors.rejectValue("user_email", "required");
        }

        if(userFindbyEmail != null) {
            errors.rejectValue("user_email", "duplication");
        }

        if(memberDTO.getUser_email().length() > RegisterConstains.MAX_EMAIL_LENGTH) {
            errors.rejectValue("user_email", "overlength");
        }

        if(!StringUtils.hasText(memberDTO.getUser_id())) {
            errors.rejectValue("user_id", "required");
        }

        if(userFindbyId != null) {
            errors.rejectValue("user_id", "duplication");
        }

        if(memberDTO.getUser_id().length() > RegisterConstains.MAX_ID_LENGTH) {
            errors.rejectValue("user_id", "overlength");
        }


        if(memberDTO.getUser_nickname().length() <= RegisterConstains.MIN_NICKNAME_LENGTH) {
            errors.rejectValue("user_nickname", "length", new Object[]{RegisterConstains.MIN_NICKNAME_LENGTH}, null);
        }

        if(userfindbyNickname != null) {
            errors.rejectValue("user_nickname", "duplication");
        }

        if(memberDTO.getUser_nickname().length() > RegisterConstains.MAX_NICKNAME_LENGTH) {
            errors.rejectValue("user_nickname", "overlength");
        }


        if(memberDTO.getUser_password().length() < RegisterConstains.MIN_PASSWORD_LENGTH) {
            if(!errors.hasFieldErrors("user_password")) {
                errors.rejectValue("user_password", "length", new Object[]{RegisterConstains.MIN_PASSWORD_LENGTH}, null);
            }
        }

        if(memberDTO.getUser_password().length() > RegisterConstains.MAX_PASSWORD) {
            if(!errors.hasFieldErrors("user_password")) {
                errors.rejectValue("user_password", "overlength");
            }
        }

        if(!memberDTO.getUser_password().equals(memberDTO.getUser_confirmPassword())) {
            if(!errors.hasFieldErrors("user_password")) {
                errors.rejectValue("user_password", "notAsSame");
                
            }
        }

        if(memberDTO.getUser_confirmPassword().length() > RegisterConstains.MAX_PASSWORD) {
            if(!errors.hasFieldErrors("user_password")) {
                errors.rejectValue("user_password", "overlength");
            }
        }

        if(memberDTO.getUser_message().length() > RegisterConstains.MAX_MESSAGE) {
            errors.rejectValue("user_message", "overlength");
        }
    }
}
