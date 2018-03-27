package com.ctl.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ctl.model.Equipamento;

public interface EquipamentoRepository extends CrudRepository<Equipamento, Long>{
	List<Equipamento> findByNomeLike(String nome);
        List<Equipamento> findBysegmentoAndTipo_id(String segmento, Long tipo_id);
        
        List<Equipamento> findBySegmentoAndTipo_idIn( String segmento, List<Long> tipoIds);
}