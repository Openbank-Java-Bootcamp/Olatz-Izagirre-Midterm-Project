package com.ironhack.demomidterm_project.repository;

import com.ironhack.demomidterm_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername (String username);
}
