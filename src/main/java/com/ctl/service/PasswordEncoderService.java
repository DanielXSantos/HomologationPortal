package com.ctl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoderService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    public String encode(String pass){
        return encoder.encode(pass);
    }
    public boolean verify(String pass, String secondPass){
        return encoder.matches(pass, secondPass);
    }
}
