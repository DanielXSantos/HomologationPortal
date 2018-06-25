package com.ctl.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ctl.model.Homologado;

public interface HomologadoRepository extends CrudRepository<Homologado, Long>{
	List<Homologado> findByNomeLike(String nome);
	List<Homologado> findByOrderByNomeAsc();
}