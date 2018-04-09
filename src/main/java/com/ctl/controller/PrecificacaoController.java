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
import com.ctl.model.Precificacao;
import com.ctl.repository.PrecificacaoRepository;
import com.ctl.util.AdvancedSearchUtil;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("/precificacao")
public class PrecificacaoController {

    @Autowired
    private PrecificacaoRepository precificacaoRepository;
    
    @Autowired
    private AdvancedSearchUtil advancedSearch;

    @GetMapping
    public String list(Model model, Authentication auth) {
        model = advancedSearch.build(model, auth);
        model.addAttribute("precificacaos", precificacaoRepository.findAll());
        return "precificacao/listar";
    }

    @GetMapping("/editar")
    public String edit(Model model, @RequestParam Long id, Authentication auth) {
        model = advancedSearch.build(model, auth);
        model.addAttribute("precificacao", precificacaoRepository.findOne(id));
        return "precificacao/formulario";
    }

    @GetMapping("/view")
    public String view(Model model, @RequestParam Long id, Authentication auth) {
        model = advancedSearch.build(model, auth);
        model.addAttribute("precificacao", precificacaoRepository.findOne(id));
        return "precificacao/descricao";
    }

    @GetMapping("/novo")
    public String novo(Model model, Authentication auth) {
        model = advancedSearch.build(model, auth);
        model.addAttribute("precificacao", new Precificacao());
        return "precificacao/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Precificacao precificacao, BindingResult bindingResult, Model model,
                         Authentication auth) {
        model = advancedSearch.build(model, auth);
        if (bindingResult.hasErrors()) {
            return "precificacao/formulario";
        }
        precificacaoRepository.save(precificacao);
        return "precificacao/listar";

    }

    @GetMapping("/buscar")
    public String buscar(Model model, @RequestParam String id, Authentication auth) {
        model = advancedSearch.build(model, auth);
        model.addAttribute("precificacao", new Equipamento());
        model.addAttribute("precificacaos", precificacaoRepository.findByIdLike("%" + id + "%"));
        return "precificacao/listar";
    }

}
