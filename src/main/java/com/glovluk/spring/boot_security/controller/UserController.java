package com.glovluk.spring.boot_security.controller;

import com.glovluk.spring.boot_security.service.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.glovluk.spring.boot_security.model.User;
import com.glovluk.spring.boot_security.service.UserService;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final UserDetails userDetails;

    @Autowired
    public UserController(UserService userService, UserDetails userDetails) {
        this.userService = userService;
        this.userDetails = userDetails;
    }

    @GetMapping("/admin")
    public String showAllUsers(Model model) {
        List<User> allUsers = userService.findAll();
        model.addAttribute("allUsers", allUsers);

        return "admin";
    }

    @GetMapping("/addNewUser")
    public String addNewUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);

        return "users-info";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        userDetails.createAndSaveUser(user);

        return "redirect:/admin";
    }

    @GetMapping("/updateinfo")
    public String updateUser(@RequestParam("userId") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));

        return "users-info";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("userId") Long id) {
        userService.delete(id);

        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String showUserPage(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated()) {
            Object principal = auth.getPrincipal();

            if (principal instanceof User) {
                model.addAttribute("user", userService.findById(((User) principal).getId()));
            }
        }

        return "user";
    }
}

