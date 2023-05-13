package com.algorithm.algoproject.validator;

import com.algorithm.algoproject.dto.BoardDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BoardValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return BoardDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BoardDTO boardDTO = (BoardDTO) target;
        String boardTitle = boardDTO.getB_title();
        String boardContent = boardDTO.getB_content();

        if(!StringUtils.hasText(boardTitle)) {
            errors.rejectValue("b_title", "required");
        }

        if(boardTitle.length() > 250) {
            errors.rejectValue("b_title", "overlength");
        }

        if(!StringUtils.hasText(boardDTO.getB_content())) {
            errors.rejectValue("b_content", "required");
        }

        if(boardContent.length() < 10) {
            errors.rejectValue("b_content", "length");
        }
    }
}
