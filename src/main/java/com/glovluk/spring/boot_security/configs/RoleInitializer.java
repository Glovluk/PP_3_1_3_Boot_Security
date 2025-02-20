package com.glovluk.spring.boot_security.configs;

import com.glovluk.spring.boot_security.model.Role;
import com.glovluk.spring.boot_security.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Component
public class RoleInitializer {

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void initializeRoles() {
        List<String> roleNames = Arrays.asList("ROLE_USER", "ROLE_ADMIN");

        for (String roleName : roleNames) {
            if (roleRepository.findByName(roleName) == null) {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
            }
        }
    }
}
