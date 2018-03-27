/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import java.util.List;

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

    @Override
    public String toString() {
        String ans = "";

        if (this.nome != null) {
            ans += this.nome + "<br>";
        }
        if (this.status != null) {
            for (String s : this.status) {
                ans += s + ", ";
            }
        }
        if (this.segmento != null) {
            for (String s : this.segmento) {
                ans += s + ", ";
            }
        }
        if (this.fabricantes != null) {
            for (Integer s : this.fabricantes) {
                ans += s.toString() + ", ";
            }
        }
        if (this.tipos != null) {
            for (Integer s : this.tipos) {
                ans += s.toString() + ", ";
            }
        }
        if (this.features != null) {
            for (Integer s : this.features) {
                ans += s.toString() + ", ";
            }
        }
        if (this.homologado != null) {
            for (Integer s : this.homologado) {
                ans += s.toString() + ", ";
            }
        }
        System.out.println("ans: " + ans);
        return ans;
    }
}
