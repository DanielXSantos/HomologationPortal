package com.ctl.controller;

import javax.validation.Valid;

import com.ctl.model.Role;
import com.ctl.repository.FabricanteRepository;
import com.ctl.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import com.ctl.model.User;
import com.ctl.repository.UserRepository;
import com.ctl.util.AdvancedSearchUtil;

import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	
	@Autowired
    private AdvancedSearchUtil search;

	@Autowired
    private UserRepository userRepository;

	@Autowired
    private FabricanteRepository fabricanteRepository;

	@Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public String index(Model model, Authentication auth) {
        model = search.build(model, auth);
        model.addAttribute("user", new User());

        return "admin/index";
    }

    @GetMapping("/register")
    public String registration(Model model, Authentication auth) {
        model = search.build(model, auth);
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("fabricantes", fabricanteRepository.findAll());
        model.addAttribute("user", new User());

        return "admin/registration";
    }

    @PostMapping("/register/save")
    public String saveUser(Model model, @Valid @ModelAttribute("user") User user,
                           BindingResult result, Authentication auth){

        try{
            userRepository.save(user);
        }catch (Exception e){
            result.addError(new ObjectError("user.email","Este email já está sendo utilizado."));
            result.rejectValue("email", "error.user","Este email já está sendo utilizado.");
                    e.printStackTrace();
        }
        if(result.hasErrors()){
            model = search.build(model, auth);
            model.addAttribute("fabricantes", fabricanteRepository.findAll());
            model.addAttribute("roles", roleRepository.findAll());
            model.addAttribute("user", user);
            return "admin/registration";
        }
        return "redirect:/admin/users";
    }
    
    @GetMapping("/editar")
    public String edit(Model model, @RequestParam Long id, Authentication auth) {
    	model = search.build(model, auth);
        model.addAttribute("user", userRepository.findOne(id));
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("fabricantes", fabricanteRepository.findAll());
        
        return "admin/registration";
    }
    
    @GetMapping("/users")
    public String listUsers(Model model, Authentication auth) {
        model = search.build(model, auth);
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("user", new User());

        return "admin/users";
    }

    @GetMapping("/users/delete")
    public String delete(Model model, @RequestParam Long id, Authentication auth){
        userRepository.delete(id);
        return "redirect:/admin/users";
    }
    
//    @PostMapping("/salvar")
//    public String salvar(@Valid User user, BindingResult bindingResult, Model model, Authentication auth) {
//        model = search.build(model, auth);
//        if (bindingResult.hasErrors()) {
//            return "admin/registration";
//        }
//        userRepository.save(user);
//
//        return "redirect:/admin/users/";
//    }

}


