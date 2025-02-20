package com.glovluk.spring.boot_security.service;

import com.glovluk.spring.boot_security.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetails extends UserDetailsService {
    @Override
    org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException;

    User createAndSaveUser(User user);
}
