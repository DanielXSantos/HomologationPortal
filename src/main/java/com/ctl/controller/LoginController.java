package com.ctl.controller;

import com.ctl.util.AdvancedSearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ctl.model.User;


@Controller
public class LoginController {

	@Autowired
	private AdvancedSearchUtil advancedSearch;

	@GetMapping(value = "/")
	public String index(Model model, Authentication auth){
		model = advancedSearch.build(model, auth);
		return "layout/main";
	}

    @GetMapping(value = "/login")
	public String login(Model model, Authentication auth){
	    if(auth != null && !auth.getName().isEmpty()){
	        return "redirect:/";
        }
		return "login";
	}
	
	// Login form with error
	  @RequestMapping("/error/loginError")
	  public String loginError(Model model) {
	    model.addAttribute("loginError", true);
	    return "/error/loginError";
	  }
	
	
	
	
	
	@GetMapping("/listar")
        public String controle(){
            return "error/404";
        }
	
	
	

}
