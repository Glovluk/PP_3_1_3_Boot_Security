package com.glovluk.spring.boot_security.controller;

import com.glovluk.spring.boot_security.model.Role;
import com.glovluk.spring.boot_security.repository.RoleRepository;
import com.glovluk.spring.boot_security.service.RoleService;
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

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final RoleRepository roleRepository;

    @Autowired
    public UserController(UserService userService, RoleService roleService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleService = roleService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/admin-bootstrap")
    public String showAllUsers(Model model) {
        List<User> allUsers = userService.findAll();
        model.addAttribute("allUsers", allUsers);

        return "admin-bootstrap";
    }

    @GetMapping("/addNewUser")
    public String addNewUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.findAll());

        return "user-info";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user,
                           @RequestParam(value = "roles", required = false) List<Long> roleId) {

        Set<Role> roles = new HashSet<>();
        if (roleId != null) {
            roles = roleId.stream()
                    .map(roleRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
        }

        user.setRoles(roles);

        userService.save(user);

        return "redirect:/admin-bootstrap";
    }

    @GetMapping("/update-info")
    public String updateUser(@RequestParam("userId") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("allRoles", roleService.findAll());

        return "user-info";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("userId") Long id) {
        userService.delete(id);

        return "redirect:/admin-bootstrap";
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

