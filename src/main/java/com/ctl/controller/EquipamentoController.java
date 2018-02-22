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
import com.ctl.repository.EquipamentoRepository;
import com.ctl.repository.FabricanteRepository;
import com.ctl.repository.FeaturesRepository;
import com.ctl.repository.PrecificacaoRepository;
import com.ctl.repository.TipoRepository;

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
		model.addAttribute("equipamento", new Equipamento());
		model.addAttribute("fabricantes", fabricanteRepository.findAll());
		model.addAttribute("featuress", featuresRepository.findAll());
		model.addAttribute("precificacaos", precificacaoRepository.findAll());
		model.addAttribute("tipos", tipoRepository.findAll());
		return "equipamento/formulario";
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid Equipamento equipamento, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("fabricantes", fabricanteRepository.findAll());
			model.addAttribute("featuress", featuresRepository.findAll());
			model.addAttribute("precificacaos", precificacaoRepository.findAll());
			model.addAttribute("tipos", tipoRepository.findAll());
			return "equipamento/formulario";
		}
		equipamentoRepository.save(equipamento);
		return "equipamento/listar";
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
