package com.algorithm.algoproject.controller;

import com.algorithm.algoproject.dto.SiteDTO;
import com.algorithm.algoproject.service.SiteLinkService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DeveloperController {

    SiteLinkService siteLinkService;

    public DeveloperController(SiteLinkService siteLinkService) {
        this.siteLinkService = siteLinkService;
    }

    @GetMapping("/developer")
    public String developer(Model model) {

        List<SiteDTO> siteLinks = siteLinkService.getSiteLinks();
        for (SiteDTO siteDTO : siteLinks) {
            System.out.println("siteDTO = " + siteDTO);
        }


        model.addAttribute("siteDTO", siteLinks);


        return "developer";
    }
}
