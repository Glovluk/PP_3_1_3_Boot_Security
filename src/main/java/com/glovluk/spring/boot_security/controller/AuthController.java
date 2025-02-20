package com.glovluk.spring.boot_security.controller;

import com.glovluk.spring.boot_security.model.User;
import com.glovluk.spring.boot_security.service.UserDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller@RequestMapping("/auth")
public class AuthController {

    private final UserDetails userDetailsService;

    @Autowired
    public AuthController(UserDetails userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping("/login")
    public String loginPage() {

        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user) {

        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@Valid @ModelAttribute("user") User user,
                                      BindingResult bindingResult,
                                      Model model) {

        if (bindingResult.hasErrors()) {
            return "auth/registration";
        }

        try {
            userDetailsService.createAndSaveUser(user);
            return "redirect:/auth/login";
        } catch (Exception e) {
            model.addAttribute("registrationError", "Registration failed. "
                    + e.getMessage());

            return "auth/registration";
        }
    }
}
