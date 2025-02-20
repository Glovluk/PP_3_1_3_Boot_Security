package com.glovluk.spring.boot_security.service;

import com.glovluk.spring.boot_security.model.User;
import com.glovluk.spring.boot_security.repository.RoleRepository;
import com.glovluk.spring.boot_security.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetails {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User, with email: "
                        + userEmail + ", not found!"));
    }

    @Override
    @Transactional
    public User createAndSaveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(roleRepository.findByName("ROLE_USER")));

        return userRepository.save(user);
    }
}
