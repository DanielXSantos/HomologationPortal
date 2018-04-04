package com.ctl.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

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
    @NotNull(message = "Forneça a data de inicio")
    private Date dataInicio;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataTermino;

    @NotEmpty
    private String segmento = "";

    private String descricao = "";

    @Transient
    private MultipartFile imagem;
    @Transient
    private MultipartFile caderno;
    @Transient
    private MultipartFile dataSheet;
    @Transient
    private MultipartFile[] files;
    @Transient
    private String filesName;

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
    private com.ctl.model.Fabricante fabricante;

    @NotEmpty
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "Equipamento_Features",
            joinColumns = {
                @JoinColumn(name = "equipamento_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "features_id")})
    private Set<com.ctl.model.Features> features = new HashSet<>();

    @NotEmpty
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "Homologado_Para",
            joinColumns = {
                @JoinColumn(name = "equipamento_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "homologado_id")})
    private Set<com.ctl.model.Features> homologado = new HashSet<>();

    @NotEmpty
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "Equipamento_Requisito",
            joinColumns = {
                @JoinColumn(name = "equipamento_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "requisito_id")})
    private Set<com.ctl.model.Requisito> requisito = new HashSet<>();

    @NotEmpty
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "Equipamento_Tipo",
            joinColumns = {
                @JoinColumn(name = "equipamento_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "tipo_id")})
    private Set<com.ctl.model.Tipo> tipo = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "precificacao_id", referencedColumnName = "id")
    private Set<com.ctl.model.Precificacao> precificacao;

    public Set<com.ctl.model.Tipo> getTipo() {
        return tipo;
    }

    public void setTipo(Set<com.ctl.model.Tipo> tipo) {
        this.tipo = tipo;
    }


    public com.ctl.model.Fabricante getFabricante() {
        return fabricante;
    }

    public void setFabricante(com.ctl.model.Fabricante fabricante) {
        this.fabricante = fabricante;
    }

    public Set<com.ctl.model.Features> getFeatures() {
        return features;
    }

    public void setFeatures(Set<com.ctl.model.Features> features) {
        this.features = features;
    }

    public Set<com.ctl.model.Precificacao> getPrecificacao() {
        return precificacao;
    }

    public void setPrecificacao(Set<com.ctl.model.Precificacao> precificacao) {
        this.precificacao = precificacao;
    }

    public Set<com.ctl.model.Features> getHomologado() {
        return homologado;
    }

    public void setHomologado(Set<com.ctl.model.Features> homologado) {
        this.homologado = homologado;
    }

    public Set<com.ctl.model.Requisito> getRequisito() {
        return requisito;
    }

    public void setRequisito(Set<com.ctl.model.Requisito> requisito) {
        this.requisito = requisito;
    }

    public MultipartFile getImagem() {
        return imagem;
    }

    public void setImagem(MultipartFile imagem) {
        this.imagem = imagem;
    }

    public MultipartFile getCaderno() {
        return caderno;
    }

    public void setCaderno(MultipartFile caderno) {
        this.caderno = caderno;
    }

    public MultipartFile getDataSheet() {
        return dataSheet;
    }

    public void setDataSheet(MultipartFile dataSheet) {
        this.dataSheet = dataSheet;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }

    public String getFilesName() {
        return filesName;
    }

    public void setFilesName(String filesName) {
        this.filesName = filesName;
    }
}
