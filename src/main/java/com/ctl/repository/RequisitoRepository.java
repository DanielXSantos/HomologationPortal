package com.ctl.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ctl.model.Requisito;

public interface RequisitoRepository extends CrudRepository<Requisito, Long>{
	List<Requisito> findByNomeLike(String nome);
	List<Requisito> findByOrderByNomeAsc();
}