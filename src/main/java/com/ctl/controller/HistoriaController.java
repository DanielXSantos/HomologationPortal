package com.ctl.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ctl.model.Historia;
import com.ctl.util.AdvancedSearchUtil;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping(value = "/historia")
public class HistoriaController {

    @Autowired
    private AdvancedSearchUtil search;

    @GetMapping
    public String list(Model model) {
        model = search.build(model);
        model.addAttribute("historia", new Historia());

        return "historia/texto";
    }
}
