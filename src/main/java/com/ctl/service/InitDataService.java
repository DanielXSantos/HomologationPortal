package com.ctl.service;

import com.ctl.model.Role;
import com.ctl.model.User;
import com.ctl.repository.RoleRepository;
import com.ctl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class InitDataService implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoderService encoder;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        if(userRepository.findByDeletedFalseAndEmailIgnoreCase("admin@admin.com") == null) {
            User admin = new User();
            Set<Role> roles = new HashSet<Role>();
            roles.addAll(roleRepository.findAll());

            roles.removeIf(role -> role.getRole().equals("FABRICANTE"));
            //roles.forEach(role -> role.setId(null));

            admin.setActive(true);
            admin.setEmail("admin@admin.com");
            admin.setExpirationDate(null);
            admin.setFabricante(null);
            admin.setName("admin");
            admin.setPassword(encoder.encode("admin"));
            admin.setRoles(roles);
            userRepository.save(admin);
            System.out.println("Add Admin User");
        }
    }
}
