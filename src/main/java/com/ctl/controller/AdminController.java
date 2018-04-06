package com.ctl.controller;

import com.ctl.util.AdvancedSearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private AdvancedSearchUtil advancedSearch;

    @GetMapping
    public String admin(Model model, Authentication auth){
        model = advancedSearch.build(model);
        return "admin/index";
    }
}
