/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ctl.model;

import javax.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author rgrj
 */
public class FormEquipamento {
    
    private Equipamento equipamento;
    @NotNull
    private MultipartFile imagem;
    @NotNull
    private MultipartFile caderno;
    @NotNull
    private MultipartFile dataSheet;
    private MultipartFile[] files;

    public FormEquipamento() {
        this.equipamento = new Equipamento();
    }
    
    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

    public MultipartFile getImagem() {
        return imagem;
    }

    public void setImagem(MultipartFile imagem) {
        this.imagem = imagem;
    }
    public void print(){
        System.out.println(equipamento.getId());
        System.out.println(equipamento.getNome());
        System.out.println(equipamento.getSegmento());
        System.out.println(equipamento.getStatus());
        System.out.println(equipamento.getData());
    }
    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
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
    
    
    
    
    
}
