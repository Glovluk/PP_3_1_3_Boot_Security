package com.glovluk.spring.boot_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.glovluk.spring.boot_security.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByName(String name);

    @Query("SELECT u.password FROM User u WHERE u.email = :email")
    String findPasswordByEmail(String email);
}
