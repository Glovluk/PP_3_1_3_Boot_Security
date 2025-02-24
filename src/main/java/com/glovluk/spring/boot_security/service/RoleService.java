package com.glovluk.spring.boot_security.service;

import com.glovluk.spring.boot_security.model.Role;

import java.util.List;

public interface RoleService {
    Role findByName(String roleName);

    Role save(Role role);

    List<Role> findAll();
}
