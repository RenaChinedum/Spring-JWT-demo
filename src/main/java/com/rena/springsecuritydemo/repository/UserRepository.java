package com.rena.springsecuritydemo.repository;

import com.rena.springsecuritydemo.entity.User;
import com.rena.springsecuritydemo.enums.Role;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);
    Optional<User> findUserByEmailOrUsername(String email, String username);


}
