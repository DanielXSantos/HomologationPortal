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

import com.ctl.model.User;
import com.ctl.repository.UserRepository;
import com.ctl.util.AdvancedSearchUtil;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	
	@Autowired
    private AdvancedSearchUtil search;

	@Autowired
    private UserRepository userRepository;

    @GetMapping
    public String list(Model model) {
        model = search.build(model);
        model.addAttribute("user", new User());

        return "admin/index";
    }

    @GetMapping("/register")
    public String registration(Model model) {
        model = search.build(model);
        model.addAttribute("user", new User());

        return "admin/registration";
    }
    
    @GetMapping("/editar")
    public String edit(Model model, @RequestParam Long id) {
    	model = search.build(model);
        model.addAttribute("user", userRepository.findOne(id));
        
        return "admin/registration";
    }
    
    @GetMapping("/users")
    public String listUsers(Model model) {
        model = search.build(model);
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("user", new User());

        return "admin/users";
    }
    
    @PostMapping("/salvar")
    public String salvar(@Valid User user, BindingResult bindingResult, Model model) {
        model = search.build(model);
        if (bindingResult.hasErrors()) {
            return "admin/registration";
        }
        userRepository.save(user);
        
        return "redirect:/admin/users/";
    }

}


