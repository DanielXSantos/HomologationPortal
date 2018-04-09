/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ctl.repository;

import com.ctl.model.Equipamento;
import form.SearchForm;
import java.util.List;

/**
 *
 * @author rgrj
 */
public interface EquipamentoSearchRepository {
    
    public List<Equipamento> search(SearchForm s);
    
}
