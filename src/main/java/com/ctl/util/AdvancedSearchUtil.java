/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ctl.util;

import com.ctl.model.Role;
import com.ctl.repository.EquipamentoRepository;
import com.ctl.repository.FabricanteRepository;
import com.ctl.repository.FeaturesRepository;
import com.ctl.repository.HomologadoRepository;
import com.ctl.repository.PrecificacaoRepository;
import com.ctl.repository.TipoRepository;
import com.ctl.repository.UserRepository;

import form.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 *
 * @author rgrj
 */
@Component
public class AdvancedSearchUtil {
     
    @Autowired
    private FabricanteRepository fabricanteRepository;
    
    @Autowired
    private FeaturesRepository featuresRepository;
    
    @Autowired
    private TipoRepository tipoRepository;
    
    @Autowired
    private HomologadoRepository homologadoRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public AdvancedSearchUtil(){
    }

    public Model build(Model model, Authentication auth){
        model = __build__(model);
        if(auth != null && auth.getAuthorities() != null) {
            for (GrantedAuthority ga : auth.getAuthorities()) {
                if (ga.getAuthority().equals("FABRICANTE")) {
                    Role r = (Role) ga;
                    model.addAttribute("fabricantesForm", fabricanteRepository.findByNomeLike(r.getUsers().getFabricante().getNome()));
                    return model;
                }
            }
        }
        model.addAttribute("fabricantesForm", fabricanteRepository.findAll());

        return model;
    }

    private Model __build__(Model model){
        model.addAttribute("featuresForm", featuresRepository.findAll());
        model.addAttribute("tiposForm", tipoRepository.findAll());
        model.addAttribute("homologadoForm", homologadoRepository.findAll());
        model.addAttribute("userForm", userRepository.findAll());
        model.addAttribute("form", new SearchForm());

        return model;
    }
    
}
