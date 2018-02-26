package com.ctl.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Equipamento {

	
	@Id
    @GeneratedValue
    private Long id;
	
	@NotEmpty
	private String nome="";
	
	@NotEmpty
	private String status="";
	
	@NotEmpty
	private String data="";
	
	@NotEmpty
	private String segmento="";	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	public String getSegmento() {
		return segmento;
	}
	public void setSegmento(String segmento) {
		this.segmento = segmento;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fabricante_id", referencedColumnName = "id")
	private Fabricante fabricante;
	
        @NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "features_id", referencedColumnName = "id")
	private Features features;
	

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "precificacao_id", referencedColumnName = "id")
	private Precificacao precificacao;
	
        @NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tipo_id", referencedColumnName = "id")
	private Tipo tipo;
	
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	public Fabricante getFabricante() {
		return fabricante;
	}
	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}
	public Features getFeatures() {
		return features;
	}
	public void setFeatures(Features features) {
		this.features = features;
	}
	public Precificacao getPrecificacao() {
		return precificacao;
	}
	public void setPrecificacao(Precificacao precificacao) {
		this.precificacao = precificacao;
	}
	
	
	
	
}
