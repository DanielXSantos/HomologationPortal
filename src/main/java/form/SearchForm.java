/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author rgrj
 */
public class SearchForm {

    private String nome;
    private List<String> status;
    private List<String> segmento;
    private List<Integer> fabricantes;
    private List<Integer> tipos;
    private List<Integer> features;
    private List<Integer> homologado;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date initialDate;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date finalDate;

    public SearchForm() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public List<String> getSegmento() {
        return segmento;
    }

    public void setSegmento(List<String> segmento) {
        this.segmento = segmento;
    }

    public List<Integer> getFabricantes() {
        return fabricantes;
    }

    public void setFabricantes(List<Integer> fabricantes) {
        this.fabricantes = fabricantes;
    }

    public List<Integer> getTipos() {
        return tipos;
    }

    public void setTipos(List<Integer> tipos) {
        this.tipos = tipos;
    }

    public List<Integer> getFeatures() {
        return features;
    }

    public void setFeatures(List<Integer> features) {
        this.features = features;
    }

    public List<Integer> getHomologado() {
        return homologado;
    }

    public void setHomologado(List<Integer> homologado) {
        this.homologado = homologado;
    }

    public Date getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }
    
    @Override
    public String toString() {
        String ans = "";

        if(this.initialDate != null){
            ans += this.initialDate.toString()+"111\n";
        }
        if(this.finalDate != null){
            ans += this.finalDate.toString()+"222\n";
        }
        System.out.println(ans);
        return ans;
    }
}
