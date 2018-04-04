package com.ctl.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ctl.model.Equipamento;
import com.ctl.model.Features;
import com.ctl.repository.FeaturesRepository;
import com.ctl.util.AdvancedSearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.ObjectError;

@Controller
@RequestMapping("/features")
public class FeaturesController {

    @Autowired
    private FeaturesRepository featuresRepository;
    
    @Autowired
    private AdvancedSearchUtil advancedSearch;

    @GetMapping
    public String list(Model model) {
        model = advancedSearch.build(model);
        model.addAttribute("featuress", featuresRepository.findAll());
        return "features/listar";
    }

    @GetMapping("/editar")
    public String edit(Model model, @RequestParam Long id) {
        model = advancedSearch.build(model);
        model.addAttribute("features", featuresRepository.findOne(id));
        return "features/formulario";
    }

    @GetMapping("/view")
    public String view(Model model, @RequestParam Long id) {
        model = advancedSearch.build(model);
        model.addAttribute("features", featuresRepository.findOne(id));
        return "features/descricao";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model = advancedSearch.build(model);
        model.addAttribute("features", new Features());
        return "features/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Features features, BindingResult bindingResult, Model model) {
        model = advancedSearch.build(model);
        if (bindingResult.hasErrors()) {
            for(ObjectError e: bindingResult.getAllErrors()){
                System.out.println(e.getCode());
            }
            return "features/formulario";
        }
        featuresRepository.save(features);
        return "redirect:/features/";

    }

    @GetMapping("/buscar")
    public String buscar(Model model, @RequestParam String nome) {
        model = advancedSearch.build(model);
        model.addAttribute("features", new Equipamento());
        model.addAttribute("featuress", featuresRepository.findByNomeLike("%" + nome + "%"));
        return "features/listar";
    }

}
