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
import com.ctl.model.Tipo;
import com.ctl.repository.TipoRepository;
import com.ctl.util.AdvancedSearchUtil;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("/tipo")
public class TipoController {

    @Autowired
    private TipoRepository tipoRepository;
    
    @Autowired
    private AdvancedSearchUtil advancedSearch;


    @GetMapping
    public String list(Model model, Authentication auth) {
        model = advancedSearch.build(model, auth);
        model.addAttribute("tipos", tipoRepository.findAll());
        return "tipo/listar";
    }

    @GetMapping("/editar")
    public String edit(Model model, @RequestParam Long id, Authentication auth) {
        model = advancedSearch.build(model, auth);
        model.addAttribute("tipo", tipoRepository.findOne(id));
        return "tipo/formulario";
    }

    @GetMapping("/view")
    public String view(Model model, @RequestParam Long id, Authentication auth) {
        model = advancedSearch.build(model, auth);
        model.addAttribute("tipo", tipoRepository.findOne(id));
        return "tipo/descricao";
    }

    @GetMapping("/novo")
    public String novo(Model model, Authentication auth) {
        model = advancedSearch.build(model, auth);
        model.addAttribute("tipo", new Tipo());
        return "tipo/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Tipo tipo, BindingResult bindingResult, Model model,
                         Authentication auth) {
        model = advancedSearch.build(model, auth);
        if (bindingResult.hasErrors()) {
            return "tipo/formulario";
        }
        tipoRepository.save(tipo);
        return "redirect:/tipo/";

    }

    @GetMapping("/buscar")
    public String buscar(Model model, @RequestParam String nome, Authentication auth) {
        model = advancedSearch.build(model, auth);
        model.addAttribute("tipo", new Equipamento());
        model.addAttribute("tipos", tipoRepository.findByNomeLike("%" + nome + "%"));
        return "tipo/listar";
    }

}
