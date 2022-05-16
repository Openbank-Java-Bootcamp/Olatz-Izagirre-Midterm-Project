package com.ironhack.demomidterm_project.repository;

import com.ironhack.demomidterm_project.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {
}
