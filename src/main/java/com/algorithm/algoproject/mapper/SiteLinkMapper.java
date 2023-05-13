package com.algorithm.algoproject.mapper;

import com.algorithm.algoproject.dto.SiteDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SiteLinkMapper {

    void insertSiteLink(SiteDTO siteDTO);

    List<SiteDTO> selectSiteLink();

}
