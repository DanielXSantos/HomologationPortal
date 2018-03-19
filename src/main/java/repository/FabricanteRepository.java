package com.ctl.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ctl.model.Fabricante;

public interface FabricanteRepository extends CrudRepository<Fabricante, Long>{
	List<Fabricante> findByNomeLike(String nome);
}

