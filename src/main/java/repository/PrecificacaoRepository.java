package com.ctl.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ctl.model.Precificacao;

public interface PrecificacaoRepository extends CrudRepository<Precificacao, Long>{
	List<Precificacao> findByIdLike(String id);
}
