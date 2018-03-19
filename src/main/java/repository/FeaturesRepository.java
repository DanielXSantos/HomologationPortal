package com.ctl.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ctl.model.Features;

public interface FeaturesRepository extends CrudRepository<Features, Long>{
	List<Features> findByNomeLike(String nome);
}