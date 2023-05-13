package com.algorithm.algoproject.mapper;


import com.algorithm.algoproject.dto.home.LevelCountDTO;
import jakarta.mail.MailSessionDefinition;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HomeMapper {

    LevelCountDTO selectHomeLevel();
}
