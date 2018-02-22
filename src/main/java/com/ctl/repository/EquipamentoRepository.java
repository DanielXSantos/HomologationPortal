package com.ctl.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ctl.model.Equipamento;

public interface EquipamentoRepository extends CrudRepository<Equipamento, Long>{
	List<Equipamento> findByNomeLike(String nome);
}
