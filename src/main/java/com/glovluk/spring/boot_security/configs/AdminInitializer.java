package com.glovluk.spring.boot_security.configs;

import com.glovluk.spring.boot_security.model.User;
import com.glovluk.spring.boot_security.repository.RoleRepository;
import com.glovluk.spring.boot_security.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@DependsOn("roleInitializer")
public class AdminInitializer {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminInitializer(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initializeAdmin() {
        if (userRepository.findByName("admin").isEmpty()) {
            User admin = new User("admin", "admin", "18", "---", "---",
                    "100", "admin@mail.ru", "admin");
            admin.setRoles(Set.of(roleRepository.findByName("ADMIN")));
            admin.setPassword(passwordEncoder.encode(admin.getPassword()));

            userRepository.save(admin);
        }
    }
}
