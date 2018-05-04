package com.ctl.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Precificacao {

	@Id
	private Long id;
	
	@NotBlank
	private String tipo="";
	
	@NotBlank
	private String part_nro="";
	
    @Lob
	@NotBlank
	private String descricao="";
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getPart_nro() {
		return part_nro;
	}
	public void setPart_nro(String part_nro) {
		this.part_nro = part_nro;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
