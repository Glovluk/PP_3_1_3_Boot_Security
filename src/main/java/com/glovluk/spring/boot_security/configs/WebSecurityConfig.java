package com.glovluk.spring.boot_security.configs;

import com.glovluk.spring.boot_security.repository.RoleRepository;
import com.glovluk.spring.boot_security.repository.UserRepository;
import com.glovluk.spring.boot_security.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final SuccessUserHandler successUserHandler;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserRepository userRepository, RoleRepository roleRepository) {
        this.successUserHandler = successUserHandler;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/auth/login-bootstrap", "/auth/registration-bootstrap").permitAll()
                        .requestMatchers("user-bootstrap").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("admin-bootstrap", "user-info").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth/login-bootstrap")
                        .loginProcessingUrl("/process_login")
                        .successHandler(successUserHandler)
                        .failureUrl("/auth/login-bootstrap?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                .userDetailsService(userDetailsService());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserServiceImpl(userRepository, roleRepository, passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}