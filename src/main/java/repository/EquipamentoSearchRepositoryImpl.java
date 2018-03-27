/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ctl.repository;

import com.ctl.model.Equipamento;
import com.ctl.model.Features;
import form.SearchForm;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rgrj
 */
@Repository
public class EquipamentoSearchRepositoryImpl implements EquipamentoSearchRepository{

    @PersistenceContext
    protected EntityManager em;
    
    
    @Transactional
    public List<Equipamento> search(SearchForm s) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Equipamento> query = builder.createQuery(Equipamento.class);
        Root r = query.from(Equipamento.class);
        
        Predicate predicate = builder.conjunction();
        
        if(s.getNome() != null && !s.getNome().isEmpty()){
            predicate = builder.and(builder.like(r.get("nome"),"%"+s.getNome()+"%"));
        }
        if(s.getStatus() != null && !s.getStatus().isEmpty()){
            predicate = builder.and(predicate, r.get("status").in(s.getStatus()));
        }
        if(s.getSegmento() != null && !s.getSegmento().isEmpty()){
            predicate = builder.and(predicate, r.get("segmento").in(s.getSegmento()));
        }
        if(s.getFabricantes() != null && !s.getFabricantes().isEmpty()){
            predicate = builder.and(predicate, r.get("fabricante").in(s.getFabricantes()));
        }
        if(s.getTipos() != null && !s.getTipos().isEmpty()){
            predicate = builder.and(predicate, r.get("tipo").in(s.getTipos()));
        }
        if(s.getFeatures() != null && !s.getFeatures().isEmpty()){
//            Subquery<Features> sub = query.subquery(Features.class);
//            Root<Features> subR = sub.from(Features.class);
//            
//            sub.where(builder.equal(r.join("features"), s.getFeatures()));
//            predicate = builder.and(predicate, builder.in(sub));
        }

        query.where(predicate);
        return em.createQuery(query).getResultList();
    }
    
}