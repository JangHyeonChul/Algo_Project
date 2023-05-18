package com.algorithm.algoproject.serviceimpl;


import com.algorithm.algoproject.dto.SiteDTO;
import com.algorithm.algoproject.mapper.SiteLinkMapper;
import com.algorithm.algoproject.service.SiteLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteLinkServiceImpl implements SiteLinkService {

    SiteLinkMapper siteLinkMapper;

    public SiteLinkServiceImpl(SiteLinkMapper siteLinkMapper) {
        this.siteLinkMapper = siteLinkMapper;
    }

    @Override
    public void writeSiteLink(SiteDTO siteDTO) {
        siteLinkMapper.insertSiteLink(siteDTO);
    }

    @Override
    public List<SiteDTO> getSiteLinks() {
        return siteLinkMapper.selectSiteLink();
    }
}
