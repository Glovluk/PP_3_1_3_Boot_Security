package com.glovluk.spring.boot_security.configs;

import com.glovluk.spring.boot_security.model.Role;
import com.glovluk.spring.boot_security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Component
public class RoleInitializer {

    private final RoleService roleService;

    @Autowired
    public RoleInitializer(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostConstruct
    public void initializeRoles() {
        List<String> roleNames = Arrays.asList("USER", "ADMIN");

        for (String roleName : roleNames) {
            if (roleService.findByName(roleName) == null) {
                Role role = new Role();
                role.setName(roleName);

                roleService.save(role);
            }
        }
    }
}
