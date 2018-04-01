package com.ctl.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Equipamento {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String nome = "";

    @NotEmpty
    private String status = "";

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataInicio;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataTermino;

    @NotEmpty
    private String segmento = "";

    private String descricao = "";

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

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

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(Date dataTermino) {
        this.dataTermino = dataTermino;
    }

    public String getSegmento() {
        return segmento;
    }

    public void setSegmento(String segmento) {
        this.segmento = segmento;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fabricante_id", referencedColumnName = "id")
    private Fabricante fabricante;

    @NotNull
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "Equipamento_Features",
            joinColumns = {
                @JoinColumn(name = "equipamento_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "features_id")})
    private Set<Features> features = new HashSet<>();

    @NotNull
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "Homologado_Para",
            joinColumns = {
                @JoinColumn(name = "equipamento_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "homologado_id")})
    private Set<Features> homologado = new HashSet<>();

    @NotNull
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "Equipamento_Requisito",
            joinColumns = {
                @JoinColumn(name = "equipamento_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "requisito_id")})
    private Set<Requisito> requisito = new HashSet<>();

    @NotNull
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "Equipamento_Tipo",
            joinColumns = {
                @JoinColumn(name = "equipamento_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "tipo_id")})
    private Set<Tipo> tipo = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "precificacao_id", referencedColumnName = "id")
    private Set<Precificacao> precificacao;

    public Set<Tipo> getTipo() {
        return tipo;
    }

    public void setTipo(Set<Tipo> tipo) {
        this.tipo = tipo;
    }


    public Fabricante getFabricante() {
        return fabricante;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }

    public Set<Features> getFeatures() {
        return features;
    }

    public void setFeatures(Set<Features> features) {
        this.features = features;
    }

    public Set<Precificacao> getPrecificacao() {
        return precificacao;
    }

    public void setPrecificacao(Set<Precificacao> precificacao) {
        this.precificacao = precificacao;
    }

    public Set<Features> getHomologado() {
        return homologado;
    }

    public void setHomologado(Set<Features> homologado) {
        this.homologado = homologado;
    }

    public Set<Requisito> getRequisito() {
        return requisito;
    }

    public void setRequisito(Set<Requisito> requisito) {
        this.requisito = requisito;
    }

}
