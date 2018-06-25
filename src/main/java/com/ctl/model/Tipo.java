package com.ctl.model;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Tipo {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String nome = "";

    @Lob
    @NotBlank
    private String descricao = "";

    @ManyToMany(mappedBy = "tipo")
    private Set<Equipamento> equipamentos;

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
