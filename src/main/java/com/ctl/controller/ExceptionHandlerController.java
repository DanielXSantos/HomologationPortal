package com.ctl.controller;

import com.ctl.util.AdvancedSearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerController {

    @Autowired
    AdvancedSearchUtil advancedSearch;

    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public String error404(Model model){
        model = advancedSearch.build(model);
        return "error/404";
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = {AccessDeniedException.class})
    public String error403(Model model){
        model = advancedSearch.build(model);
        return "error/loginError";
    }

}
