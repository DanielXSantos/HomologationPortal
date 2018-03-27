/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ctl.util;

import com.ctl.repository.EquipamentoRepository;
import com.ctl.repository.FabricanteRepository;
import com.ctl.repository.FeaturesRepository;
import com.ctl.repository.HomologadoRepository;
import com.ctl.repository.PrecificacaoRepository;
import com.ctl.repository.TipoRepository;
import form.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    
    public AdvancedSearchUtil(){
    }
    
    public Model build(Model model){
        
        model.addAttribute("fabricantesForm", fabricanteRepository.findAll());
        model.addAttribute("featuresForm", featuresRepository.findAll());
        model.addAttribute("tiposForm", tipoRepository.findAll());
        model.addAttribute("homologadoForm", homologadoRepository.findAll());
        model.addAttribute("form", new SearchForm());
        
        return model;
    }
    
}
