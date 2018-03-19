package com.ctl.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ctl.model.Equipamento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EquipamentoRepository extends CrudRepository<Equipamento, Long>{
	List<Equipamento> findByNomeLike(String nome);
        List<Equipamento> findBysegmentoAndTipo_id(String segmento, Long tipo_id);
        
        List<Equipamento> findBySegmentoAndTipo_idIn( String segmento, List<Long> tipoIds);
//        @Query("SELECT o FROM equipamento o WHERE segmento = :seg AND tipo_id in :ids")
//        List<Equipamento> findByTypeIdsAndSegmento(@Param("seg") String segmento, @Param("ids")List<Long> tipoIds);
}