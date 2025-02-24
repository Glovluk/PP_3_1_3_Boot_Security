package com.glovluk.spring.boot_security.configs;

import com.glovluk.spring.boot_security.model.User;
import com.glovluk.spring.boot_security.service.RoleService;
import com.glovluk.spring.boot_security.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@DependsOn("roleInitializer")
public class AdminInitializer {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminInitializer(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void initializeAdmin() {
        if (userService.findByName("admin").isEmpty()) {
            User admin = new User("admin", "admin", "18", "---", "---",
                    "100", "admin@mail.ru", "admin");
            admin.setRoles(Set.of(roleService.findByName("ADMIN")));

            userService.save(admin);
        }
    }
}
