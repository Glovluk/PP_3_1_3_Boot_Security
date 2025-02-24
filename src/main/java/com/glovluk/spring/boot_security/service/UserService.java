package com.glovluk.spring.boot_security.service;


import com.glovluk.spring.boot_security.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    List<User> findAll();

    User findById(Long id);

    void delete(Long id);

    @Override
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

    User save(User user);

    Optional<User> findByName(String name);
}
