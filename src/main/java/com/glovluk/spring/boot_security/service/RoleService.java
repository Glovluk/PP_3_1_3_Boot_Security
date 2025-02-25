package com.glovluk.spring.boot_security.service;

import com.glovluk.spring.boot_security.model.Role;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleService {
    Role findByName(String roleName);

    Role save(Role role);

    List<Role> findAll();

    Optional<Role> findById(@NonNull Long roleId);

    Set<Role> findAllRolesByIds(List<Long> roleIds);
}
