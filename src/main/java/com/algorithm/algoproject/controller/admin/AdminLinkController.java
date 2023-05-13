package com.algorithm.algoproject.controller.admin;


import com.algorithm.algoproject.dto.SiteDTO;
import com.algorithm.algoproject.service.SiteLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminLinkController {

    @Autowired
    SiteLinkService siteLinkService;

    @GetMapping("/link")
    public String adminLink(@ModelAttribute("siteLinkDTO") SiteDTO siteDTO) {


        return "/admin/admin-site";
    }

    @PostMapping("/link")
    public String adminLinkSubmit(SiteDTO siteDTO) {
        siteLinkService.writeSiteLink(siteDTO);

        System.out.println("siteDTO = " + siteDTO);

        return "redirect:/developer";
    }
}
