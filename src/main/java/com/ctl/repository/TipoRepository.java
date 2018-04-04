package com.ctl.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ctl.model.Tipo;



public interface TipoRepository extends CrudRepository<Tipo, Long>{
	List<Tipo> findByNomeLike(String nome);
}
