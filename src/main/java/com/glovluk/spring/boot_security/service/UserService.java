package com.glovluk.spring.boot_security.service;



import com.glovluk.spring.boot_security.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(Long id);

    void save(User user);

    void delete(Long id);
}
