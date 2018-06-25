/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ctl.util;

import com.ctl.model.Role;
import com.ctl.model.User;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.stream.Collectors;

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
        model = __build__(model, auth);
        if(auth != null && auth.getAuthorities() != null) {
            for (GrantedAuthority ga : auth.getAuthorities()) {
                if (ga.getAuthority().equals("FABRICANTE")) {
                    Role r = (Role) ga;
                    model.addAttribute("fabricantesForm",
                            fabricanteRepository.findByNomeLike(
                                    r.getUsers().stream().filter(
                                            user -> user.getEmail().equals(
                                                    auth.getName())).collect(
                                                            Collectors.toList()
                                                    ).get(0).getFabricante().getNome()
                            )
                    );
                    return model;
                }
            }
        }
        model.addAttribute("fabricantesForm", fabricanteRepository.findByOrderByNomeAsc());

        return model;
    }

    private Model __build__(Model model,Authentication auth){
        Object o;
        try{
            o = auth.getPrincipal();
        } catch (Exception e){
            o = null;
        }

        model.addAttribute("featuresForm", featuresRepository.findByOrderByNomeAsc());
        model.addAttribute("tiposForm", tipoRepository.findByOrderByNomeAsc());
        model.addAttribute("homologadoForm", homologadoRepository.findByOrderByNomeAsc());
        //model.addAttribute("userForm", userRepository.findAll());
        model.addAttribute("form", new SearchForm());
        if(o instanceof User) {
            model.addAttribute("userName", ((User)o).getName());
        }else if (o != null){
            model.addAttribute("userName", userRepository.findByDeletedFalseAndEmailIgnoreCase((String)o).getName());
        }
        return model;
    }
    
}
