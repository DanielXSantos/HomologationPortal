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
import com.ctl.model.Homologado;
import com.ctl.repository.HomologadoRepository;
import com.ctl.util.AdvancedSearchUtil;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("/homologado")
public class HomologadoController {

    @Autowired
    private HomologadoRepository homologadoRepository;

    @Autowired
    private AdvancedSearchUtil advancedSearch;

    @GetMapping
    public String list(Model model, Authentication auth) {
        model = advancedSearch.build(model, auth);
        model.addAttribute("homologados", homologadoRepository.findAll());
        return "homologado/listar";
    }

    @GetMapping("/editar")
    public String edit(Model model, @RequestParam Long id, Authentication auth) {
        model = advancedSearch.build(model, auth);
        model.addAttribute("homologado", homologadoRepository.findOne(id));
        return "homologado/formulario";
    }

    @GetMapping("/view")
    public String view(Model model, @RequestParam Long id, Authentication auth) {
        model = advancedSearch.build(model, auth);
        model.addAttribute("homologado", homologadoRepository.findOne(id));
        return "homologado/descricao";
    }

    @GetMapping("/novo")
    public String novo(Model model, Authentication auth) {
        model = advancedSearch.build(model, auth);
        model.addAttribute("homologado", new Homologado());
        return "homologado/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Homologado homologado, BindingResult bindingResult, Model model,
                         Authentication auth) {
        model = advancedSearch.build(model, auth);
        if (bindingResult.hasErrors()) {
            return "homologado/formulario";
        }
        homologadoRepository.save(homologado);
        return "redirect:/homologado/";

    }

    @GetMapping("/buscar")
    public String buscar(Model model, @RequestParam String nome, Authentication auth) {
        model = advancedSearch.build(model, auth);
        model.addAttribute("homologado", new Equipamento());
        model.addAttribute("homologados", homologadoRepository.findByNomeLike("%" + nome + "%"));
        return "homologado/listar";
    }

}
