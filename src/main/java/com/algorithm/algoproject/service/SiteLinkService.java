package com.algorithm.algoproject.service;


import com.algorithm.algoproject.dto.SiteDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SiteLinkService {

    void writeSiteLink(SiteDTO siteDTO);

    List<SiteDTO> getSiteLinks();


}
