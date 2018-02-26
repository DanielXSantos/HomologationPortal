package com.ctl.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ctl.model.Equipamento;
import com.ctl.model.FormEquipamento;
import com.ctl.repository.EquipamentoRepository;
import com.ctl.repository.FabricanteRepository;
import com.ctl.repository.FeaturesRepository;
import com.ctl.repository.PrecificacaoRepository;
import com.ctl.repository.TipoRepository;
import java.io.File;
import java.io.IOException;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value={"/equipamento"})
public class EquipamentoController {
	
	@Autowired
	private EquipamentoRepository equipamentoRepository;
	private FabricanteRepository fabricanteRepository;
	private FeaturesRepository featuresRepository;
	private PrecificacaoRepository precificacaoRepository;
	private TipoRepository tipoRepository;
	
	public EquipamentoController(EquipamentoRepository equipamentoRepository, FabricanteRepository fabricanteRepository, FeaturesRepository featuresRepository, PrecificacaoRepository precificacaoRepository, TipoRepository tipoRepository) {
		this.equipamentoRepository = equipamentoRepository;
		this.fabricanteRepository = fabricanteRepository;
		this.featuresRepository = featuresRepository;
		this.precificacaoRepository = precificacaoRepository;
		this.tipoRepository = tipoRepository;
	}
	
	@GetMapping
	public String list(Model model) {
		model.addAttribute("equipamento", new Equipamento());
		model.addAttribute("equipamentos", equipamentoRepository.findAll());
		return "equipamento/listar";
	}
	
	@GetMapping("/editar")
	public String edit(Model model, @RequestParam Long id) {
		model.addAttribute("equipamento", equipamentoRepository.findOne(id));
		model.addAttribute("fabricantes", fabricanteRepository.findAll());
		model.addAttribute("featuress", featuresRepository.findAll());
		model.addAttribute("precificacaos", precificacaoRepository.findAll());
		model.addAttribute("tipos", tipoRepository.findAll());
		return "equipamento/formulario";
	}
	
	@GetMapping("/view")
	public String view(Model model, @RequestParam Long id) {
		model.addAttribute("equipamento", equipamentoRepository.findOne(id));
		model.addAttribute("fabricantes", fabricanteRepository.findAll());
		model.addAttribute("featuress", featuresRepository.findAll());
		model.addAttribute("precificacaos", precificacaoRepository.findAll());
		model.addAttribute("tipos", tipoRepository.findAll());
		return "equipamento/descricao";
	}
	
	
	@GetMapping("/novo")
	public String novo(Model model) {
		model.addAttribute("equipamento", new FormEquipamento());
		model.addAttribute("fabricantes", fabricanteRepository.findAll());
		model.addAttribute("featuress", featuresRepository.findAll());
		model.addAttribute("precificacaos", precificacaoRepository.findAll());
		model.addAttribute("tipos", tipoRepository.findAll());
		return "equipamento/formulario";
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid @ModelAttribute("equipamento") FormEquipamento equipamento,
                            BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
                        model.addAttribute("equipamento", new FormEquipamento());
			model.addAttribute("fabricantes", fabricanteRepository.findAll());
			model.addAttribute("featuress", featuresRepository.findAll());
			model.addAttribute("precificacaos", precificacaoRepository.findAll());
			model.addAttribute("tipos", tipoRepository.findAll());
			return "equipamento/formulario";
		} 
                
                try{
                    equipamento.setEquipamento(equipamentoRepository.save(equipamento.getEquipamento()));
                    String path = System.getProperty("user.dir")+"/uploads/"+equipamento.getEquipamento().getId();
                    // Abrindo a pasta, e criando se não existe
                    File f = new File(path);
                    if(!f.exists())
                        f.mkdirs();
                    
                    // salvando imagem
                    String imgType = equipamento.getImagem().getOriginalFilename();
                    int pointer = imgType.lastIndexOf(".");
                    if(pointer == -1)
                        throw new Exception("Imagem não inserida");
                    imgType = imgType.substring(pointer);
                    File img = new File(path + "/image" + imgType);
                    img.createNewFile();
                    equipamento.getImagem().transferTo(img);
                    
                    // verifica se o usuário inseriu o caderno e o dataSheet
                    // todo: validar formulário
                    if(equipamento.getCaderno().getOriginalFilename().isEmpty() &&
                            equipamento.getDataSheet().getOriginalFilename().isEmpty())
                        throw new Exception("Caderno e DataSheet é obrigatorio");
                    
                    // salvando caderno de Testes
                    File caderno = new File(path+"/caderno.pdf");
                    caderno.createNewFile();
                    equipamento.getCaderno().transferTo(caderno);
                    
                    // salvando dataSheet
                    File dataSheet = new File(path+"/dataSheet.pdf");
                    dataSheet.createNewFile();
                    equipamento.getDataSheet().transferTo(dataSheet);
                    
                    // Salvando anexos
                    if(equipamento.getFiles().length > 0){
                        new File(path+"/anexos").mkdirs();
                        for(MultipartFile file: equipamento.getFiles()){
                            file.transferTo(new File(path+"/anexos/"+file.getOriginalFilename()));
                        }
                    }
                    
                }catch(Exception e){
                    File directory = new File(System.getProperty("user.dir")+"/uploads/"+equipamento.getEquipamento().getId());
                    try{
                        FileUtils.cleanDirectory(directory);
                        FileUtils.forceDelete(directory);
                    }catch(Exception ex){}
                    equipamentoRepository.delete(equipamento.getEquipamento());
                    e.printStackTrace();
                    model.addAttribute("equipamento", new FormEquipamento());
                    model.addAttribute("fabricantes", fabricanteRepository.findAll());
                    model.addAttribute("featuress", featuresRepository.findAll());
                    model.addAttribute("precificacaos", precificacaoRepository.findAll());
                    model.addAttribute("tipos", tipoRepository.findAll());
                    return "equipamento/formulario";
//                    return "equipamento/listar";

                }
//                return "redirect:/"; 
		return "redirect:/equipamento";
	}
	
	@GetMapping("/excluir")
	public String excluir(Model model, @RequestParam Long id) {
		equipamentoRepository.delete(id);
		return "redirect:/"; 
	}
	
	@GetMapping("/buscar")
	public String buscar(Model model, @RequestParam String nome) {
		model.addAttribute("equipamento", new Equipamento());
		model.addAttribute("equipamentos", equipamentoRepository.findByNomeLike("%" + nome + "%"));
		return "equipamento/formulario";
	}

}
