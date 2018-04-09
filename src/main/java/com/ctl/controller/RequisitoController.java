package com.ctl.controller;

import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ctl.model.Equipamento;
import com.ctl.model.Requisito;
import com.ctl.repository.RequisitoRepository;
import com.ctl.util.AdvancedSearchUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("/requisito")
public class RequisitoController {

    @Autowired
    private RequisitoRepository requisitoRepository;
    
    @Autowired
    private AdvancedSearchUtil advancedSearch;
    
    @GetMapping
    public String list(Model model, Authentication auth) {
        model = advancedSearch.build(model, auth);
        model.addAttribute("requisitos", requisitoRepository.findAll());
        return "requisito/listar";
    }

    @GetMapping("/editar")
    public String edit(Model model, @RequestParam Long id, Authentication auth) {
        model = advancedSearch.build(model, auth);
        model.addAttribute("requisito", requisitoRepository.findOne(id));
        return "requisito/formulario";
    }

    @GetMapping("/view")
    public String view(Model model, @RequestParam Long id, Authentication auth) {
        model = advancedSearch.build(model, auth);
        model.addAttribute("requisito", requisitoRepository.findOne(id));
        return "requisito/descricao";
    }

    @GetMapping("/novo")
    public String novo(Model model, Authentication auth) {
        model = advancedSearch.build(model, auth);
        model.addAttribute("requisito", new Requisito());
        return "requisito/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Requisito requisito, BindingResult bindingResult, Model model,
                         Authentication auth) {
        model = advancedSearch.build(model, auth);
        if (bindingResult.hasErrors()) {
            return "requisito/formulario";
        }
        requisitoRepository.save(requisito);
        return "redirect:/requisito/";

    }

    @GetMapping("/buscar")
    public String buscar(Model model, @RequestParam String nome, Authentication auth) {
        model = advancedSearch.build(model, auth);
        model.addAttribute("requisito", new Equipamento());
        model.addAttribute("requisitos", requisitoRepository.findByNomeLike("%" + nome + "%"));
        return "requisito/listar";
    }

}
