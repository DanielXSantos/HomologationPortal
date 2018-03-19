package com.ctl.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ctl.model.Equipamento;
import com.ctl.model.Fabricante;
import com.ctl.repository.FabricanteRepository;

@Controller
@RequestMapping("/fabricante")
public class FabricanteController {

	private FabricanteRepository fabricanteRepository;
	
    public FabricanteController(FabricanteRepository fabricanteRepository) {
        this.fabricanteRepository = fabricanteRepository;
    }
	
	@GetMapping
	public String list(Model model) {
		model.addAttribute("fabricantes", fabricanteRepository.findAll());
		return "fabricante/listar";
	}
	
	@GetMapping("/editar")
	public String edit(Model model, @RequestParam Long id) {
		model.addAttribute("fabricante", fabricanteRepository.findOne(id));
		return "fabricante/formulario";
	}
	
	@GetMapping("/view")
	public String view(Model model, @RequestParam Long id) {
		model.addAttribute("fabricante", fabricanteRepository.findOne(id));
		return "fabricante/formulario";
	}
	
	@GetMapping("/novo")
	public String novo(Model model) {
		model.addAttribute("fabricante", new Fabricante());
		return "fabricante/formulario";
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid Fabricante fabricante, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "fabricante/formulario";
		}
		fabricanteRepository.save(fabricante);
		return "redirect:/fabricante/";
		}
	
	@GetMapping("/buscar")
	public String buscar(Model model, @RequestParam String nome) {
		model.addAttribute("fabricante", new Equipamento());
		model.addAttribute("fabricantes", fabricanteRepository.findByNomeLike("%" + nome + "%"));
		return "fabricante/listar";
	}
	
}
	
