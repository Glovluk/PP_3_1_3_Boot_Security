package com.glovluk.spring.boot_security.controller;

import com.glovluk.spring.boot_security.model.User;
import com.glovluk.spring.boot_security.service.UserService;
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

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
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
            if (userService.save(user) != null) {
                return "redirect:/auth/login";
            } else {
                return "redirect:/auth/registration";
            }
        } catch (Exception e) {
            model.addAttribute("registrationError", "Registration failed. "
                    + e.getMessage());

            return "auth/registration";
        }
    }
}
