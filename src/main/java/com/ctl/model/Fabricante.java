package com.ctl.model;

import javax.persistence.*;

import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

@Entity
public class Fabricante {

	@Id
	@GeneratedValue
	private Long id;
	
	@NotBlank
	private String nome="";
	
	@NotBlank
	private String email="";
	
	@NotBlank
	private String telefone="";

	@OneToMany(targetEntity = User.class, mappedBy = "fabricante", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List user;
	
	
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public List getUser() {
		return user;
	}
	public void setUser(List user) {
		this.user = user;
	}
}
