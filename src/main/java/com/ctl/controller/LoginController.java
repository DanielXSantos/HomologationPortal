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
		model = advancedSearch.build(model);
		return "layout/main";
	}

    @GetMapping(value = "/login")
	public String login(Model model, Authentication auth){
	    if(auth != null && !auth.getName().isEmpty()){
	        return "redirect:/";
        }
		return "login";
	}
	
	/*
	
	 @GetMapping("/editar")
	    public String edit(Model model) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 
	        model.addAttribute("user", userService.findUserByEmail(auth.getName()));
	        return "/registration";
	    }
	*/


	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}
	
//	@RequestMapping(value = "/admin/registration", method = RequestMethod.POST)
//	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
//		ModelAndView modelAndView = new ModelAndView();
//		User userExists = userService.findUserByEmail(user.getEmail());
//		if (userExists != null) {
//			bindingResult
//					.rejectValue("email", "error.user",
//							"There is already a user registered with the email provided");
//		}
//		if (bindingResult.hasErrors()) {
//			modelAndView.setViewName("registration");
//		} else {
//			userService.saveUser(user);
//			modelAndView.addObject("successMessage", "User has been registered successfully");
//			modelAndView.addObject("user", new User());
//			modelAndView.setViewName("registration");
//
//		}
//		return modelAndView;
//	}
	
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
