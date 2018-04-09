package com.ctl.service;

import com.ctl.model.User;
import com.ctl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class AuthProviderService implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoderService encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String pass  = authentication.getCredentials().toString();

        User user = userRepository.findByDeletedFalseAndEmailIgnoreCase(email);

        if(user != null && !user.isDeleted() && encoder.verify(pass,user.getPassword())){
            if (user.isActive()
                    && !user.isAccountNonExpired()) {
                Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
                for(GrantedAuthority e: authorities){
                    System.out.println(e.getAuthority());
                }
                return new UsernamePasswordAuthenticationToken(email, pass, authorities);
            } else {
                throw new BadCredentialsException("Este usuário esta desativado.");
            }

        }else{
            throw new UsernameNotFoundException("Login e/ou senha inválidos.");
        }

    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
